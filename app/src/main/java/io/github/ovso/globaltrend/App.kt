package io.github.ovso.globaltrend

import android.app.Application

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    AppInit.timber()
    AppInit.prefs(this)
    AppInit.ad(this)
  }
}