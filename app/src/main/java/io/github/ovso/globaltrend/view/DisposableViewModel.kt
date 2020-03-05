package io.github.ovso.globaltrend.view

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

open class DisposableViewModel : ViewModel() {
  val compositeDisposable = CompositeDisposable()
  fun addDisposable(d: Disposable) {
    compositeDisposable.add(d)
  }

  fun clearDisposable() {
    compositeDisposable.clear()
    Timber.d("clearDisposable()")
  }
}