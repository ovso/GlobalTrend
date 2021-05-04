package io.github.ovso.globaltrend.utils

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.orhanobut.logger.Logger
import io.github.ovso.globaltrend.BuildConfig


class AppOpenManager(app: Application) {
  private var appOpenAd: AppOpenAd? = null
  lateinit var loadCallback: AppOpenAdLoadCallback
  private val application: Application = app
  var callback: ((AppOpenAd?) -> Unit)? = null

  /** Request an ad  */
  fun fetchAd() {
    // Have unused ad, no need to fetch another.
    // Have unused ad, no need to fetch another.
    if (isAdAvailable) {
      return
    }
    loadCallback = object : AppOpenAdLoadCallback() {
      /**
       * Called when an app open ad has loaded.
       *
       * @param ad the loaded app open ad.
       */
      override fun onAdLoaded(ad: AppOpenAd?) {
        appOpenAd = ad
        callback?.invoke(ad)
      }

      /**
       * Called when an app open ad has failed to load.
       *
       * @param loadAdError the error.
       */
      override fun onAdFailedToLoad(loadAdError: LoadAdError?) {
        // Handle the error.
        Logger.e(loadAdError?.message ?: "")
        callback?.invoke(null)
      }

    }
    val request: AdRequest = adRequest
    AppOpenAd.load(
      application,
//      Keys.ADMOB_APP_OPENING.value,
      BuildConfig.ADS_OPENING_UNIT_ID,
      request,
      AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
      loadCallback
    )

  }

  /** Creates and returns ad request.  */
  private val adRequest: AdRequest
    get() = AdRequest.Builder().build()

  /** Utility method that checks if ad exists and can be shown.  */
  private val isAdAvailable: Boolean
    get() = appOpenAd != null

  companion object {
    private const val LOG_TAG = "AppOpenManager"
  }

}
