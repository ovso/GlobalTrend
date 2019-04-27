package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.util.Xml.Encoding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.utils.LocaleUtils
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

class DailyTrendViewModel : ViewModel() {
  private val  compositeDisposable = CompositeDisposable()
  val elementsLiveData = MutableLiveData<Elements>()
  val refreshLiveData = MutableLiveData<Boolean>()

  fun fetchList() {
    refreshLiveData.value = true
    addDisposable(
      Single.fromCallable {
        Jsoup.connect("https://trends.google.co.kr/trends/trendingsearches/daily/rss")
          .data("geo", URLEncoder.encode(LocaleUtils.country, Encoding.UTF_8.toString()))
          .parser(Parser.xmlParser())
          .timeout(1000 * 10)
          .get()
      }.map {
        it.getElementsByTag("item")
      }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(onError = {
          Timber.e(it)
          refreshLiveData.value = false
        }, onSuccess = {
          elementsLiveData.value = it
          refreshLiveData.value = false
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