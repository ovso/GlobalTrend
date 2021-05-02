package io.github.ovso.globaltrend.view.ui.detail

import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.utils.RxBusEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class TrendDetailViewModel : ViewModel() {
  private val compositeDisposable = CompositeDisposable()

  private val _thumb = MutableStateFlow("")
  val thumb: StateFlow<String> = _thumb
  private val _desc = MutableStateFlow("")
  val desc: StateFlow<String> = _desc

  init {
    observe()
  }

  private fun observe() {
    RxBusBehavior.listen(RxBusEvent.OnTrendItemClick::class.java)
      .subscribe {
        Timber.d(it.toString())
//        setupItem(it.item)
        val items = it.item?.getElementsByTag("ht:news_item")
        Logger.d("elementsByTag: ${items?.first()}")
        val first = items?.first()
        Logger.d(first?.getElementsByTag("ht:news_item_title"))
        Logger.d(first?.getElementsByTag("ht:news_item_snippet"))
        Logger.d(first?.getElementsByTag("news_item_url"))
        Logger.d(first?.getElementsByTag("ht:news_item_source"))

/*
        this.imageviewItemThumb.load(item?.getElementsByTag("ht:picture")?.text())
        this.textviewItemTitle.text = item?.getElementsByTag("title")?.text()
        this.textviewItemTraffic.text = item?.getElementsByTag("ht:approx_traffic")?.text()
*/
      _thumb.value = it.item?.getElementsByTag("ht:picture")?.text() ?: ""
      _desc.value = first?.getElementsByTag("ht:news_item_snippet")?.text() ?: ""

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
