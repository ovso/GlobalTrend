package io.github.ovso.globaltrend.view.ui.web

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class WebViewModel : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  val titleLiveData = MutableLiveData<String>()
  val urlObField = ObservableField<String>()
  val webViewClient = WebViewClient()
  val webChromeClient = WebChromeClient()

  init {
    toRxBusObservable()
  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus2.toObservable().subscribeBy(onNext = { any ->
        (any as? RxBusWeb)?.let {
          titleLiveData.value = it.title
          urlObField.set(it.url)
        }
      }, onError = {

      })
    )
  }

  private fun addDisposable(d: Disposable) {
    compositeDisposable.add(d)
  }

  private fun clearDisposable() {
    compositeDisposable.clear()
  }

  override fun onCleared() {
    super.onCleared()
    clearDisposable()
  }

  class RxBusWeb(var title: String?, var url: String?)
}
