package io.github.ovso.globaltrend.view.ui.country

import android.content.Context
import android.util.Xml.Encoding
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.DisposableViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
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
  }

  private val observables = mutableListOf<Observable<Document>>()
  private val documents = mutableListOf<Element>()
  private var startTime = 0L
  private var endTime = 0L
  private val titles = mutableListOf<String>()
  fun getObservables() {
    startTime = Calendar.getInstance().timeInMillis
    val countryCodes = context.resources.getStringArray(R.array.country_codes).toMutableList()
    val observables = getObservables(countryCodes)
    addDisposable(
      Flowable.fromIterable(observables)
        .parallel()
        .runOn(Schedulers.computation())
        .map {
          titles.add(it.blockingFirst().getElementsByTag("item").first().getElementsByTag("title").text())
          titles
        }
        .sequential()
        .subscribeBy(
          onError = Timber::e,
          onNext = {
            Timber.d("it size = ${it.size}")
          },
          onComplete = {
            Timber.d("onComplete")
            Timber.d("titles size = ${titles.size}")
            for (title in titles) {
              Timber.d("title = $title")
            }

            endTime = Calendar.getInstance().timeInMillis
            Timber.d("between time = ${(endTime - startTime) / 1000}")
          }
        )
    )
  }

  private fun getObservables(countryCodes: MutableList<String>): MutableList<Observable<Document>> {
    val mutableListOf = mutableListOf<Observable<Document>>()
    for (countryCode in countryCodes) {
      mutableListOf.add(
        Observable.fromCallable {
          Jsoup.connect(rssUrl)
            .data("geo", URLEncoder.encode(countryCode, Encoding.UTF_8.toString()))
            .parser(Parser.xmlParser()).timeout(timeout).get()
        }
      )
    }
    return mutableListOf
  }
}