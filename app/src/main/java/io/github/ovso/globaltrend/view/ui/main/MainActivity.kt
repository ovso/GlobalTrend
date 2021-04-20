package io.github.ovso.globaltrend.view.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.material.navigation.NavigationView
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import de.psdev.licensesdialog.LicensesDialog
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.R.id
import io.github.ovso.globaltrend.R.string
import io.github.ovso.globaltrend.databinding.ActivityMainBinding
import io.github.ovso.globaltrend.extension.loadAdaptiveBanner
import io.github.ovso.globaltrend.utils.PrefsKey
import io.github.ovso.globaltrend.view.MyAdView
import io.github.ovso.globaltrend.view.base.DataBindingActivity
import io.github.ovso.globaltrend.view.ui.country.CountryActivity
import io.github.ovso.globaltrend.view.ui.dailytrend.DailyTrendFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_banner_container.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class MainActivity : DataBindingActivity(), NavigationView.OnNavigationItemSelectedListener {
  private val viewModel: MainViewModel by viewModels()
  private val binding: ActivityMainBinding by binding(R.layout.activity_main)

  @Inject
  lateinit var analytics: String

  private lateinit var interstitialAd: InterstitialAd

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.d("analytics = $analytics")
    binding.apply {
      lifecycleOwner = this@MainActivity
      viewModel = this@MainActivity.viewModel
    }
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayShowCustomEnabled(true)

    val toggle = ActionBarDrawerToggle(
      this,
      drawer_layout, toolbar,
      string.navigation_drawer_open,
      string.navigation_drawer_close
    )
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()
    nav_view.setNavigationItemSelectedListener(this)
    replaceFragment()

    lifecycleScope.launch {
      when (loadedAd()) {
        true -> {
//          interstitialAd.show()
        }
        false -> {

        }
      }
    }

    loadAdaptiveBanner(ff_all_banner_container, getString(string.ads_banner_unit_id))
  }

  private suspend fun loadedAd() = suspendCoroutine<Boolean> {
    interstitialAd = MyAdView.getAdmobInterstitialAd(this)
    interstitialAd.adListener = object : AdListener() {

      override fun onAdLoaded() {
        super.onAdLoaded()
        it.resumeWith(Result.success(true))
      }

      override fun onAdFailedToLoad(p0: Int) {
        super.onAdFailedToLoad(p0)
        it.resumeWith(Result.failure(Exception("Load fail")))
      }
    }

  }

  private fun replaceFragment() {
    supportFragmentManager.beginTransaction().replace(
      id.framelayout_fragment_container,
      DailyTrendFragment.newInstance(),
      DailyTrendFragment::class.simpleName
    ).commitNow()
  }

  override fun onBackPressed() =
    when (drawer_layout.isDrawerOpen(GravityCompat.START)) {
      true -> drawer_layout.closeDrawer(GravityCompat.START)
      false -> super.onBackPressed()
    }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds pagedList to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem) =
  // Handle action bar item clicks here. The action bar will
  // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    when (item.itemId) {
      id.action_countries -> {
        AlertDialog.Builder(this)
          .setSingleChoiceItems(
            R.array.country_names,
            Prefs.getInt(PrefsKey.COUNTRY_INDEX.key, 0),
            viewModel.onDialogClickListener
          ).show()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
      id.nav_country -> startActivity(Intent(this, CountryActivity::class.java))
      id.nav_share -> navigateToShare()
      id.nav_reivew -> navigateToReview()
      id.nav_opensource -> showOpensourceLicense()
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  private fun showOpensourceLicense() {
    LicensesDialog.Builder(this)
      .setNotices(R.raw.notices)
      .build()
      .show()
  }

  private fun navigateToShare() {
    val intent = Intent(Intent.ACTION_SEND)
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    intent.putExtra(Intent.EXTRA_TITLE, "Share")
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, "market://details?value=$packageName")
    startActivity(Intent.createChooser(intent, "App share"))
  }

  private fun navigateToReview() {
    val intent = Intent(Intent.ACTION_VIEW).apply {
      val uriString = "https://play.google.com/store/apps/details?id=$packageName"
      data = Uri.parse(uriString)
      setPackage("com.android.vending")
    }
    try {
      startActivity(intent)
    } catch (e: ActivityNotFoundException) {
      Timber.e(e)
    }
  }
}
