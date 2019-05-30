package io.github.ovso.globaltrend.view.ui.country

import android.content.Context
import android.util.Xml.Encoding
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.DisposableViewModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import timber.log.Timber
import java.net.URLEncoder
import java.util.Calendar

class CountryViewModel(private var context: Context) : DisposableViewModel() {
  private val rssUrl = "https://trends.google.co.kr/trends/trendingsearches/daily/rss"
  private val timeout = 1000 * 10
  val isLoading = ObservableField<Boolean>()
  private val country: String? = null
  private val elementsLiveData = MutableLiveData<Elements>()

  fun fetchList() {
    isLoading.set(true)
/*
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
*/
  }

  private val observables = mutableListOf<Observable<Document>>()
  private val documents = mutableListOf<Element>()
  private var startTime = 0L
  private var endTime = 0L
  fun getObservables() {
    startTime = Calendar.getInstance().timeInMillis
    val countryCodes = context.resources.getStringArray(R.array.country_codes).toMutableList()
    addDisposable(
      Observable.fromIterable(countryCodes)
        .subscribeOn(Schedulers.io())
        .subscribeBy(
          onError = Timber::e,
          onNext = { addObservable(it) },
          onComplete = { concatObservables() }
        )
    )
  }

  private fun addObservable(countryCode: String) {
    observables.add(
      Observable.fromCallable {
        Jsoup.connect(rssUrl).data("geo", URLEncoder.encode(countryCode, Encoding.UTF_8.toString()))
          .parser(Parser.xmlParser()).timeout(timeout).get()
      }
    )
  }

  private fun concatObservables() {
    addDisposable(
      Observable.concat(observables)
        .map {
          documents.add(it.getElementsByTag("item").first())
        }.subscribeBy(
          onError = Timber::e,
          onComplete = {
            Timber.d("documents size = ${documents.size}")
          })

    )
  }
}