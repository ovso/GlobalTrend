package io.github.ovso.globaltrend.utils

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

object RxBusBehavior {
  private val publisher = BehaviorSubject.create<Any>()

  fun publish(event: Any) {
    publisher.onNext(event)
  }

  // Listen should return an Observable and not the publisher
  // Using ofType we filter only events that match that class type
  fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}
