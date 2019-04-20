package io.github.ovso.globaltrend

import android.app.Application
import io.github.ovso.globaltrend.utils.AppInit

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    AppInit.timber()
    AppInit.prefs(this)
    AppInit.ad(this)
  }
}