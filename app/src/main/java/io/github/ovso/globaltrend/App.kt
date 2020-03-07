package io.github.ovso.globaltrend

import android.app.Application
import io.github.ovso.globaltrend.utils.AppInit
import io.github.ovso.globaltrend.utils.rx.RxBus
import io.github.ovso.globaltrend.utils.rx.RxBus2
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {
  companion object {
    val rxBus = RxBus()
    val rxBus2 = RxBus2()
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
