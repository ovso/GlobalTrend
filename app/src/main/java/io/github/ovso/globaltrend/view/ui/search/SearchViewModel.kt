package io.github.ovso.globaltrend.view.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.jsoup.nodes.Element

class SearchViewModel : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  val titleLiveData = MutableLiveData<String>()

  init {
    toRxBusObservable()
  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus2.toObservable()
        .subscribeBy { any ->
          (any as? Element).let {
            it?.getElementsByTag("title")?.text().let {
              titleLiveData.value = it!!
            }
          }
        }
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
  }
}