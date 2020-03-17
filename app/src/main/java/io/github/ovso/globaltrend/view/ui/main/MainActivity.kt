package io.github.ovso.globaltrend.view.ui.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import com.pixplicity.easyprefs.library.Prefs
import de.psdev.licensesdialog.LicensesDialog
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.R.id
import io.github.ovso.globaltrend.R.string
import io.github.ovso.globaltrend.databinding.ActivityMainBinding
import io.github.ovso.globaltrend.utils.PrefsKey
import io.github.ovso.globaltrend.view.ui.country.CountryActivity
import io.github.ovso.globaltrend.view.ui.dailytrend.DailyTrendFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
  private lateinit var viewModel: MainViewModel

  private val adSize: AdSize
    get() {
      val display = windowManager.defaultDisplay
      val outMetrics = DisplayMetrics()
      display.getMetrics(outMetrics)

      val density = outMetrics.density

      var adWidthPixels = ads_container.width.toFloat()
      if (adWidthPixels == 0f) {
        adWidthPixels = outMetrics.widthPixels.toFloat()
      }

      val adWidth = (adWidthPixels / density).toInt()
      return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding =
      DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    viewModel = provideViewModel()
    binding.viewModel = viewModel
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

    showBanner()
    // https://groups.google.com/forum/#!topic/google-admob-ads-sdk/N02N_ftO7xk
    // blogging
  }

  private fun showBanner() {
    adView = AdView(this)
    ads_container.addView(adView)

    fun loadBanner() {
      adView.adUnitId = getString(string.ads_banner_unit_id)
      adView.adSize = adSize
      val adRequest = AdRequest.Builder().build()
      adView.loadAd(adRequest)
    }

    loadBanner()
  }

  private lateinit var adView: AdView;

  private fun replaceFragment() {
    supportFragmentManager.beginTransaction().replace(
      id.framelayout_fragment_container,
      DailyTrendFragment.newInstance(),
      DailyTrendFragment::class.simpleName
    ).commitNow()
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): MainViewModel =
    ViewModelProvider(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel() as T
    }).get(MainViewModel::class.java)

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
