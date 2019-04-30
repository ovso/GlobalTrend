package io.github.ovso.globaltrend.view.ui.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.view.adapter.MainAdapter.RxBusElement
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class SearchViewModel(val context: Context) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  val titleLiveData = MutableLiveData<String>()

  init {
    toRxBusObservable()
  }

  fun fetchList() {

  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus2.toObservable()
        .subscribeBy { any ->
          (any as? RxBusElement).let {
            titleLiveData.value = it?.element?.getElementsByTag("title")?.text()
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
    clearDisposable()
    super.onCleared()
  }
}