package io.github.ovso.globaltrend.view.ui.detail

import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.utils.RxBus
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.utils.RxBusEvent
import io.reactivex.disposables.CompositeDisposable

class TrendDetailViewModel : ViewModel() {
  private val compositeDisposable = CompositeDisposable()
  init {
      observe()
  }

  private fun observe() {
//    RxBusBehavior.listen(RxBusEvent.)
  }

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }
}
