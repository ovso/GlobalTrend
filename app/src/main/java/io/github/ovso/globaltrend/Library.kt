package io.github.ovso.globaltrend

import android.content.Context
import android.content.ContextWrapper
import com.google.android.gms.ads.MobileAds
import com.pixplicity.easyprefs.library.Prefs
import timber.log.Timber

object Library {

  fun init(context: Context) {
    timber()
    prefs(context)
    ads(context)
  }

  private fun ads(context: Context) {
    MobileAds.initialize(context, context.getString(R.string.ads_app_id))
  }

  private fun prefs(context: Context) {
    Prefs.Builder()
      .setContext(context)
      .setMode(ContextWrapper.MODE_PRIVATE)
      .setPrefsName(context.packageName)
      .setUseDefaultSharedPreference(true)
      .build()
  }

  fun timber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

}
