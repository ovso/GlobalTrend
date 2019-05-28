package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.content.Context
import android.util.Xml.Encoding
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.PrefsKey
import io.github.ovso.globaltrend.view.ui.main.MainViewModel.RxBusCountryIndex
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import timber.log.Timber
import java.net.URLEncoder
import io.reactivex.rxkotlin.subscribeBy as subscribeBy1

class DailyTrendViewModel(var context: Context) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  val elementsLiveData = MutableLiveData<Elements>()
  private var country: String? = null
  val isLoading = ObservableBoolean()

  init {
    country = getCountry()
    toRxBusObservable()
  }

  private fun getCountry() = context.resources.getStringArray(R.array.country_codes)[
      Prefs.getInt(PrefsKey.COUNTRY_INDEX.key, 0)]

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus.toObservable().subscribeBy {
        (it as? RxBusCountryIndex)?.let {
          country = context.resources.getStringArray(R.array.country_codes)[it.index]
          onRefresh()
        }
      }
    )
  }

  fun onRefresh() {
    elementsLiveData.value = null
    fetchList()
  }

  fun fetchList() {
    isLoading.set(true)
    addDisposable(
      Single.fromCallable {
        Jsoup.connect("https://trends.google.co.kr/trends/trendingsearches/daily/rss")
          .data("geo", URLEncoder.encode(country, Encoding.UTF_8.toString()))
          .parser(Parser.xmlParser())
          .timeout(1000 * 10)
          .get()
      }.map {
        it.getElementsByTag("item")
      }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(onError = {
          Timber.e(it)
          isLoading.set(false)
        }, onSuccess = {
          elementsLiveData.value = it
          isLoading.set(false)
        })
    )
  }

  fun addDisposable(disposable: Disposable) {
    compositeDisposable.add(disposable)
  }

  fun clearDisposable() {
    compositeDisposable.clear()
  }

  override fun onCleared() {
    super.onCleared()
    clearDisposable()
  }
}