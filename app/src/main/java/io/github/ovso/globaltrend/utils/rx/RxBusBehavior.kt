package io.github.ovso.globaltrend.utils.rx

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

@Deprecated("")
class RxBusBehavior {
  private val bus = BehaviorSubject.create<Any>()

  fun send(o: Any) {
    bus.onNext(o)
  }

  fun toObservable(): Observable<Any> {
    return bus
  }

  fun hasObservable(): Boolean {
    return bus.hasObservers()
  }
}
