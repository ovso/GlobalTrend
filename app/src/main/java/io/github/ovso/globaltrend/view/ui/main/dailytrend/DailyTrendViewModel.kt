package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.content.Context
import android.util.Xml.Encoding
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.LocaleUtils
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

class DailyTrendViewModel(var context: Context) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  val elementsLiveData = MutableLiveData<Elements>()
  private var countryCode: String? = null
  private var countryIndex: Int = 0
  val isLoading = ObservableBoolean()
  val titleLiveData = MutableLiveData<String>()

  init {
    countryIndex = getCountryIndex()
    countryCode = context.resources.getStringArray(R.array.country_codes)[countryIndex]
    Prefs.putInt(PrefsKey.COUNTRY_INDEX.key, countryIndex)
    titleLiveData.value = context.resources.getStringArray(R.array.country_names)[countryIndex]
    toRxBusObservable()
  }

  private fun getCountryIndex(): Int {
    val indexForPrefs = Prefs.getInt(PrefsKey.COUNTRY_INDEX.key, -1)
    return when (indexForPrefs == -1) {
      true -> {
        val indexOf = context.resources.getStringArray(R.array.country_codes)
          .indexOf(LocaleUtils.country)
        when (indexOf == -1) {
          true -> 0
          false -> indexOf
        }
      }
      false -> indexForPrefs
    }
  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus.toObservable().subscribeBy {
        (it as? RxBusCountryIndex)?.let { o ->
          Prefs.putInt(PrefsKey.COUNTRY_INDEX.key, o.index)
          titleLiveData.value = context.resources.getStringArray(R.array.country_names)[o.index]
          countryCode = context.resources.getStringArray(R.array.country_codes)[o.index]
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
          .data("geo", URLEncoder.encode(countryCode, Encoding.UTF_8.toString()))
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

  private fun addDisposable(disposable: Disposable) {
    compositeDisposable.add(disposable)
  }

  private fun clearDisposable() {
    compositeDisposable.clear()
  }

  override fun onCleared() {
    super.onCleared()
    clearDisposable()
  }
}