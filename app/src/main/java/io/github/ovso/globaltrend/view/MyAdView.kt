package io.github.ovso.globaltrend.view

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import io.github.ovso.globaltrend.R

object MyAdView {

  fun getAdmobInterstitialAd(context: Context): InterstitialAd {
    val interstitialAd = InterstitialAd(context)
    interstitialAd.adUnitId = context.getString(R.string.ads_interstitial_id)
    val adRequestBuilder = AdRequest.Builder()
    interstitialAd.loadAd(adRequestBuilder.build())
    return interstitialAd
  }
}
