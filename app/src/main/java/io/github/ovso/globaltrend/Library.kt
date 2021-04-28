package io.github.ovso.globaltrend

import android.content.Context
import com.google.android.gms.ads.MobileAds
import timber.log.Timber

object Library {

  fun init(context: Context) {
    timber()
    ads(context)
  }

  private fun ads(context: Context) {
    MobileAds.initialize(context, context.getString(R.string.ads_app_id))
  }

  private fun timber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

}
