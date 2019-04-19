package io.github.ovso.globaltrend

import android.content.Context
import android.content.ContextWrapper
import com.google.android.gms.ads.MobileAds
import com.pixplicity.easyprefs.library.Prefs
import timber.log.Timber

object AppInit {
  fun timber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  fun prefs(context: Context) {
    Prefs.Builder()
        .setContext(context)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(context.packageName)
        .setUseDefaultSharedPreference(true)
        .build()
  }

  fun ad(context: Context) {
    MobileAds.initialize(context, Ads.ADMOB_APP_ID.value)
  }
}