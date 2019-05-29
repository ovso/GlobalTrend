package io.github.ovso.globaltrend.view.ui.country

import android.content.Context
import android.util.Xml.Encoding
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.DisposableViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import timber.log.Timber
import java.net.URLEncoder

class CountryViewModel(private var context: Context) : DisposableViewModel() {
  private val rssUrl = "https://trends.google.co.kr/trends/trendingsearches/daily/rss"
  private val timeout = 1000 * 10
  val isLoading = ObservableField<Boolean>()
  private val country: String? = null
  private val elementsLiveData = MutableLiveData<Elements>()

  fun fetchList() {
    isLoading.set(true)

    addDisposable(
      Single.fromCallable {
        Jsoup.connect(rssUrl)
          .data("geo", URLEncoder.encode(country, Encoding.UTF_8.toString()))
          .parser(Parser.xmlParser()).timeout(1000 * 10)
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

  fun getObservables() {
    val countryCodes = context.resources.getStringArray(R.array.country_codes).toMutableList()

    addDisposable(
      Observable.fromIterable(countryCodes).subscribe {
        Observable.fromCallable {
          Jsoup.connect(rssUrl).data("geo", URLEncoder.encode(it, Encoding.UTF_8.toString()))
            .parser(Parser.xmlParser()).timeout(timeout).get()
        }.map {
          it.getElementsByTag("item")
        }.subscribeOn(Schedulers.io()).subscribe {

        }
        
      }
    )

  }
}