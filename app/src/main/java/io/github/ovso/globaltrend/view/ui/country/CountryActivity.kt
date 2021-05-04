package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.extension.loadAdaptiveBanner
import io.github.ovso.globaltrend.extension.showInterstitialAds
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_banner_container.*

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

    showInterstitialAds()
    loadAdaptiveBanner(ff_all_banner_container, getString(R.string.ads_banner_unit_id))
  }
}
