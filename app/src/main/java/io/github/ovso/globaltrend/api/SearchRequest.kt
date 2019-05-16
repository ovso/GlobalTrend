package io.github.ovso.globaltrend.api

import io.github.ovso.globaltrend.api.model.CustomSearch
import io.reactivex.Single

class SearchRequest : BaseRequest<SearchService>() {

  override val apiClass: Class<SearchService>
    get() = SearchService::class.java
  override val isInterceptor: Boolean
    get() = false

  fun search(query: String, start: Int): Single<CustomSearch> {
    return api.search(query, start)
  }

}