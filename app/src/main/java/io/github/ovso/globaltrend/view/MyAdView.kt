package io.github.ovso.globaltrend.view

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.github.ovso.globaltrend.R

object MyAdView {

  fun getAdmobInterstitialAd(context: Context, callback:InterstitialAdLoadCallback) {
    val unitId = context.getString(R.string.ads_interstitial_id)
    InterstitialAd.load(
      context,
      unitId,
      AdRequest.Builder().build(),
      callback
    )
  }
}
