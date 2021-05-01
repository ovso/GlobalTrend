package io.github.ovso.globaltrend.view.ui.combobox

import android.content.SharedPreferences
import android.util.Xml.Encoding
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.LocaleUtils
import io.github.ovso.globaltrend.utils.PrefsKey
import io.github.ovso.globaltrend.utils.ResourceProvider
import io.github.ovso.globaltrend.view.ui.main.MainViewModel.RxBusCountryIndex
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import timber.log.Timber
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class ComboBoxViewModel @Inject constructor(
  private val resourceProvider: ResourceProvider,
  private val prefs: SharedPreferences
) : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  private val _items = MutableStateFlow<Elements?>(null)
  val items: StateFlow<Elements?> = _items
  private var countryCode: String? = null
  private var countryIndex: Int = 0
  private val _isLoadingFlow = MutableStateFlow(false)
  val isLoadingFlow: StateFlow<Boolean> = _isLoadingFlow
  private val _titleStateFlow = MutableStateFlow("")
  val titleStateFlow: StateFlow<String> = _titleStateFlow

  init {
    countryIndex = getCountryIndex()
    countryCode = resourceProvider.getStringArray(R.array.country_codes)[countryIndex]
    prefs.edit { putInt(PrefsKey.COUNTRY_INDEX.key, countryIndex) }
    _titleStateFlow.value = resourceProvider.getStringArray(R.array.country_names)[countryIndex]
    toRxBusObservable()
    fetchList()
  }

  private fun getCountryIndex(): Int {
    val indexForPrefs = prefs.getInt(PrefsKey.COUNTRY_INDEX.key, -1)
//    val indexForPrefs = -1
    return when (indexForPrefs == -1) {
      true -> {
        val indexOf = resourceProvider.getStringArray(R.array.country_codes)
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
          prefs.edit { putInt(PrefsKey.COUNTRY_INDEX.key, o.index) }
          _titleStateFlow.value = resourceProvider.getStringArray(R.array.country_names)[o.index]
          countryCode = resourceProvider.getStringArray(R.array.country_codes)[o.index]
          onRefresh()
        }
      }
    )
  }

  fun onRefresh() {
    fetchList()
  }

  private fun fetchList() {
    _isLoadingFlow.value = true
    viewModelScope.launch(Dispatchers.IO) {
      val document =
        Jsoup.connect("https://trends.google.co.kr/trends/trendingsearches/daily/rss")
          .data("geo", URLEncoder.encode(countryCode, Encoding.UTF_8.toString()))
          .parser(Parser.xmlParser())
          .timeout(1000 * 10)
          .get()
      _items.value = document.getElementsByTag("item")
      _isLoadingFlow.value = false
    }
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

  fun onItemClick(it: Element?) {
    Timber.d("it: ${it.toString()}")
  }
}
