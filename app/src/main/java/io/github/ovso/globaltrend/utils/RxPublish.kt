package io.github.ovso.globaltrend.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class RxPublish {
  private val publisher = PublishSubject.create<Any>()

  fun publish(event: Any) {
    publisher.onNext(event)
  }

  // Listen should return an Observable and not the publisher
  // Using ofType we filter only events that match that class type
  fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}
