package io.github.ovso.globaltrend

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.ovso.globaltrend.app.MyExceptionHandler
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
    Library.init(this)
    RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())
    crashHandling(this)
  }

  private fun crashHandling(app: Application) {
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
