package io.github.ovso.globaltrend.view.ui.country

import android.content.Context
import android.util.Xml.Encoding
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.DisposableViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import timber.log.Timber
import java.net.URLEncoder
import java.util.Calendar

class CountryViewModel(private var context: Context) : DisposableViewModel() {
  private val rssUrl = "https://trends.google.co.kr/trends/trendingsearches/daily/rss"
  private val timeout = 1000 * 10
  val isLoading = ObservableField<Boolean>()
  private val elements = Elements()
  val elementsLiveData = MutableLiveData<Elements>()
  private var startTime = 0L

  fun fetchList() {
    isLoading.set(true)
    startTime = Calendar.getInstance().timeInMillis
    val countryCodes = context.resources.getStringArray(R.array.country_codes).toMutableList()
    addDisposable(
      Flowable.fromIterable(getObservables(countryCodes))
        .parallel()
        .runOn(Schedulers.computation())
        .map {
          elements.add(it.blockingFirst().getElementsByTag("item").first())
        }
        .sequential()
        .subscribeBy(
          onError = {
            Timber.e(it)
            isLoading.set(false)
          },
          onComplete = {
            isLoading.set(false)
            elementsLiveData.postValue(elements) // postValue -> mainThread
          }
        )
    )
  }

  fun onRefresh() {
    elementsLiveData.value = null
    fetchList()
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

  override fun onCleared() {
    super.onCleared()
    clearDisposable()
  }
}