package io.github.ovso.globaltrend.view.ui.main

import android.content.Context
import android.content.DialogInterface
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.LocaleUtils
import io.github.ovso.globaltrend.utils.PrefsKey

class MainViewModel(private var context: Context) : ViewModel() {
  var checkedItem: Int = -1
  val titleObField = ObservableField<String>()

  init {
    checkedItem = getCountryIndex()
    Prefs.putInt(PrefsKey.COUNTRY_INDEX.key, checkedItem)
    changeTitle(checkedItem)
  }

  private fun changeTitle(countryIndex: Int) {
    val countryName = context.resources.getStringArray(R.array.country_names)[countryIndex]
    titleObField.set("$countryName 일일 급상승 검색어")
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

  var onDialogClickListener = DialogInterface.OnClickListener { dialog, which ->
    dialog?.dismiss()
    checkedItem = which
    Prefs.putInt(PrefsKey.COUNTRY_INDEX.key, which)
    App.rxBus.send(RxBusCountryIndex(which))
    changeTitle(which)
  }

  class RxBusCountryIndex(var index: Int)

}