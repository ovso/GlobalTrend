package io.github.ovso.globaltrend.view.ui.main

import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainViewModel(private var context: Context) : ViewModel() {
  var checkedItem: Int = 0
  private val compositeDisposable = CompositeDisposable()

  var onDialogClickListener = DialogInterface.OnClickListener { dialog, which ->
    dialog?.dismiss()
    checkedItem = which
    App.rxBus.send(RxBusCountryIndex(which))
  }

  fun addDisposable(d: Disposable) {
    compositeDisposable.add(d)
  }

  fun clearDisposable() {
    compositeDisposable.clear()
  }

  override fun onCleared() {
    super.onCleared()
    clearDisposable()
  }

  class RxBusCountryIndex(var index: Int) {
  }
}