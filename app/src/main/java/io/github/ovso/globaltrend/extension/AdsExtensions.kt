package io.github.ovso.globaltrend.extension

import android.app.Activity
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.AppOpenManager

fun Activity.adaptiveBannerAdSize(): AdSize {
  val display = windowManager.defaultDisplay
  val outMetrics = DisplayMetrics()
  display.getMetrics(outMetrics)

  val density = outMetrics.density

  var adWidthPixels = 0f
  if (adWidthPixels == 0f) {
    adWidthPixels = outMetrics.widthPixels.toFloat()
  }

  val adWidth = (adWidthPixels / density).toInt()
  return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
}

fun Fragment.adaptiveBannerAdSize(): AdSize {
  val context = requireActivity()
  val display = context.windowManager.defaultDisplay
  val outMetrics = DisplayMetrics()
  display.getMetrics(outMetrics)

  val density = outMetrics.density

  var adWidthPixels = 0f
  if (adWidthPixels == 0f) {
    adWidthPixels = outMetrics.widthPixels.toFloat()
  }

  val adWidth = (adWidthPixels / density).toInt()
  return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
}

fun Activity.loadAdaptiveBanner(container: ViewGroup, unitId: String) {
  val adView = AdView(container.context)
  container.addView(adView)

  fun load() {
    adView.adUnitId = unitId
    adView.adSize = adaptiveBannerAdSize()
    val adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)
  }

  load()
}

fun Fragment.loadAdaptiveBanner(container: ViewGroup, unitId: String) {
  val adView = AdView(container.context)
  container.addView(adView)

  fun load() {
    adView.adUnitId = unitId
    adView.adSize = adaptiveBannerAdSize()
    val adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)
  }

  load()
}

/*
fun Activity.showOpeningAds(fullScreenContentCallback: FullScreenContentCallback) {
  AppOpenManager(this.application).also { manager ->
    manager.callback = { ad ->
      ad?.fullScreenContentCallback = fullScreenContentCallback
      ad?.show(this@showOpeningAds)
    }
    manager.fetchAd()
  }
}
*/
fun Activity.showOpeningAds(callback: (AppOpenAd?) -> Unit) {
  AppOpenManager(this.application).also { manager ->
    manager.callback = callback
    manager.fetchAd()
  }
}

fun Activity.showInterstitialAds(fullScreenContentCallback: FullScreenContentCallback? = null) {
  val unitId = getString(R.string.ads_interstitial_id)
  InterstitialAd.load(
    this,
    unitId,
    AdRequest.Builder().build(),
    object : InterstitialAdLoadCallback() {
      override fun onAdLoaded(ad: InterstitialAd) {
        super.onAdLoaded(ad)
        ad.fullScreenContentCallback = fullScreenContentCallback
        ad.show(this@showInterstitialAds)
      }

      override fun onAdFailedToLoad(error: LoadAdError) {
        super.onAdFailedToLoad(error)
      }
    }
  )

}
//https://groups.google.com/forum/#!topic/google-admob-ads-sdk/N02N_ftO7xk
