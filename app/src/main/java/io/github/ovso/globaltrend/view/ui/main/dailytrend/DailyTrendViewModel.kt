package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.util.Xml.Encoding
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.utils.LocaleUtils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import timber.log.Timber
import java.net.URLEncoder

class DailyTrendViewModel : ViewModel() {
  val compositeDisposable = CompositeDisposable()
  /*
      Thread {
      val doc = Jsoup.connect("https://trends.google.co.kr/trends/trendingsearches/daily/rss")
        .data("geo", URLEncoder.encode(LocaleUtils.country, Encoding.UTF_8.toString()))
        .parser(Parser.xmlParser())
        .timeout(3000)
        .get()
      var elementsByTag = doc.getElementsByTag("item")
      elementsByTag.first()
      for (element in elementsByTag) {
        println(element.getElementsByTag("title"))
      }
    }.start()

  */

  init {
    fetchList()
  }

  fun fetchList() {
    addDisposable(
      Single.create<Document> {
        Jsoup.connect("https://trends.google.co.kr/trends/trendingsearches/daily/rss")
          .data("geo", URLEncoder.encode(LocaleUtils.country, Encoding.UTF_8.toString()))
          .parser(Parser.xmlParser())
          .timeout(3000)
          .get()
      }.map {
        for (element in it.getElementsByTag("item")) {
          Timber.d(element.getElementsByTag("title").toString())
        }
      }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe()
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