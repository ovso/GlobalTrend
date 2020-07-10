package io.github.ovso.globaltrend.utils

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import com.google.android.gms.ads.MobileAds
import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.globaltrend.BuildConfig
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.app.MyExceptionHandler
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
    MobileAds.initialize(context, context.getString(R.string.ads_app_id))
  }

  fun crashHandling(app: Application) {
    val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { _, _ ->
      // Crashlytics에서 기본 handler를 호출하기 때문에 이중으로 호출되는것을 막기위해 빈 handler로 설정
    }
    val fabricExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler(
      MyExceptionHandler(
        app,
        defaultExceptionHandler,
        fabricExceptionHandler
      )
    )
  }
}
