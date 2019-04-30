package io.github.ovso.globaltrend.api

import com.google.gson.JsonObject
import io.github.ovso.globaltrend.api.model.CustomSearch
import io.reactivex.Single

class SearchRequest : BaseRequest<SearchService>() {

  override val apiClass: Class<SearchService>
    get() = SearchService::class.java
  override val isInterceptor: Boolean
    get() = false

  fun search(query: String): Single<CustomSearch> {
    return api.search(query)
  }

  companion object {

    fun createParam(
      start: Int,
      rows: Int
    ): JsonObject {
      val params = JsonObject()
      params.addProperty("start", start)
      params.addProperty("rows", rows)
      return params
    }
  }
}