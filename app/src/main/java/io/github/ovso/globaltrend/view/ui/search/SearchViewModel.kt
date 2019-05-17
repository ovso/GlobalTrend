package io.github.ovso.globaltrend.view.ui.search

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.api.SearchRequest
import io.github.ovso.globaltrend.api.model.Item
import io.github.ovso.globaltrend.view.adapter.MainAdapter.RxBusElement
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SearchViewModel(application: Application) : AndroidViewModel(application) {
  private val compositeDisposable = CompositeDisposable()
  val titleLiveData = MutableLiveData<String>()
  val isLoading = ObservableBoolean()
  var pagedList: LiveData<PagedList<Item>>? = null

  init {
    toRxBusObservable()
  }

  fun onRefresh() {
    //fetchList()
  }

  private fun fetchList() {
    isLoading.set(true)
    pagedList = LivePagedListBuilder<Int, Item>(
      MyDataSourceFactory(titleLiveData.value!!),
      PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(false).build()
    ).build()

  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus2.toObservable()
        .subscribeBy { any ->
          (any as? RxBusElement)?.let {
            titleLiveData.value = it.element.getElementsByTag("title")?.text()
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

  class MyDataSourceFactory(val q: String) :
    DataSource.Factory<Int, Item>() {

    override fun create(): DataSource<Int, Item> {
      return MyDataSource(q)
    }
  }

  class MyDataSource(val q: String) : PositionalDataSource<Item>() {
    private var searchRequest: SearchRequest = SearchRequest()
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Item>) {
      var disposable =
        searchRequest
          .search(q, 1)
          .subscribeBy(
            onSuccess = {
              callback.onResult(it.items, 0)
            },
            onError = Timber::e
          )
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Item>) {
      var disposable =
        searchRequest
          .search(q, params.startPosition)
          .subscribeBy(
            onSuccess = {
              callback.onResult(it.items)
            },
            onError = Timber::e
          )

    }

  }

}