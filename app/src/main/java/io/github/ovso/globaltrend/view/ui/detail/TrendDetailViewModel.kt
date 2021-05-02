package io.github.ovso.globaltrend.view.ui.detail

import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.utils.RxBusEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jsoup.select.Elements
import timber.log.Timber

class TrendDetailViewModel : ViewModel() {
  private val compositeDisposable = CompositeDisposable()

  private val _thumb = MutableStateFlow<String?>(null)
  val thumb: StateFlow<String?> = _thumb

  private val _items = MutableStateFlow<Elements?>(null)
  val items: StateFlow<Elements?> = _items

  private val _title = MutableStateFlow<String?>(null)
  val title: StateFlow<String?> = _title

  init {
    observe()
  }

  private fun observe() {
    RxBusBehavior.listen(RxBusEvent.OnTrendItemClick::class.java)
      .subscribe {
        Timber.d(it.toString())
        _title.value = it.item?.getElementsByTag("title")?.text()
        _items.value = it.item?.getElementsByTag("ht:news_item")
        _thumb.value = it.item?.getElementsByTag("ht:picture")?.text()

      }.addTo(compositeDisposable)
  }

  /*
      //ht:news_item_title
    //ht:news_item_snippet // description
    //ht:news_item_url     // 출처웹주소.\ㅋ
    //ht:news_item_source // 출처

   */

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
