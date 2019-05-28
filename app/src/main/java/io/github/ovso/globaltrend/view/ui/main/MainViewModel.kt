package io.github.ovso.globaltrend.view.ui.main

import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.ViewModel
import com.pixplicity.easyprefs.library.Prefs
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.utils.PrefsKey

class MainViewModel(private var context: Context) : ViewModel() {
  var checkedItem: Int = Prefs.getInt(PrefsKey.COUNTRY_INDEX.key, 0)

  var onDialogClickListener = DialogInterface.OnClickListener { dialog, which ->
    dialog?.dismiss()
    checkedItem = which
    Prefs.putInt(PrefsKey.COUNTRY_INDEX.key, which)
    App.rxBus.send(RxBusCountryIndex(which))
  }

  class RxBusCountryIndex(var index: Int) {
  }

}