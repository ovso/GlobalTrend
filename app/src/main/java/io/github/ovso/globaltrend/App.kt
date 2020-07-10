package io.github.ovso.globaltrend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.ovso.globaltrend.utils.AppInit
import io.github.ovso.globaltrend.utils.rx.RxBus
import io.github.ovso.globaltrend.utils.rx.RxBusBehavior
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins

@HiltAndroidApp
class App : Application() {
  companion object {
    val rxBus = RxBus()
    val rxBus2 = RxBusBehavior()
  }

  override fun onCreate() {
    super.onCreate()
    AppInit.timber()
    AppInit.prefs(this)
    AppInit.ad(this)
    RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())
    AppInit.crashHandling(this)
  }
}
