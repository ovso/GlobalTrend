package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.Ads
import kotlinx.android.synthetic.main.activity_country.linearlayout_country_content_container
import kotlinx.android.synthetic.main.activity_web.toolbar

class CountryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_country)
    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_fragment_container, CountryFragment.newInstance())
        .commitNow()
    }

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    setupAds()
  }

  private fun setupAds() {
    val adView = AdView(this)
    adView.adSize = AdSize.BANNER
    adView.adUnitId = Ads.ADMOB_BANNER_UNIT_ID.value
    adView.loadAd(AdRequest.Builder().build())
    linearlayout_country_content_container.addView(adView)
  }

}