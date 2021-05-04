package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.extension.loadAdaptiveBanner
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

    val unitId = getString(R.string.ads_interstitial_id)
    InterstitialAd.load(
      this,
      unitId,
      AdRequest.Builder().build(),
      object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(ad: InterstitialAd) {
          super.onAdLoaded(ad)
          ad.show(this@CountryActivity)
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
          super.onAdFailedToLoad(error)
        }
      }
    )




    loadAdaptiveBanner(ff_all_banner_container, getString(R.string.ads_banner_unit_id))
  }
}
