package io.github.ovso.globaltrend.view.ui.main

import android.content.DialogInterface
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.utils.Ads
import io.github.ovso.globaltrend.utils.AppInit

class MainViewModel : ViewModel() {

  var onDialogClickListener = DialogInterface.OnClickListener { dialog, which ->
    dialog.dismiss()
    App.rxBus.send(RxBusCountryIndex(which))
  }

  class RxBusCountryIndex(var index: Int)

}