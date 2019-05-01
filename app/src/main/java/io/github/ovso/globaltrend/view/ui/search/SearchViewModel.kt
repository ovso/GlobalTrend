package io.github.ovso.globaltrend.view.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.api.SearchRequest
import io.github.ovso.globaltrend.api.model.Item
import io.github.ovso.globaltrend.view.adapter.MainAdapter.RxBusElement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SearchViewModel(application: Application) : AndroidViewModel(application) {
  private val compositeDisposable = CompositeDisposable()
  val titleLiveData = MutableLiveData<String>()
  val itemsLiveData = MutableLiveData<List<Item>>()

  init {
    toRxBusObservable()
  }

  private fun fetchList() {
    val req = SearchRequest()
    addDisposable(
      req.search(titleLiveData.value!!).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
          onSuccess = {
            itemsLiveData.value = it.items
          }, onError = {

          }
        )
    )
  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus2.toObservable()
        .subscribeBy { any ->
          (any as? RxBusElement).let {
            titleLiveData.value = it?.element?.getElementsByTag("title")?.text()
            fetchList()
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