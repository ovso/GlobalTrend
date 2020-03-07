package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.MyAdView
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
    linearlayout_country_content_container.addView(MyAdView.getAdmobBannerView(this))
  }
}
