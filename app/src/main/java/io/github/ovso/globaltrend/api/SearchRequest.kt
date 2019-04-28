package io.github.ovso.globaltrend.api

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.reactivex.Single

class SearchRequest : BaseRequest<SearchService>() {

  override val apiClass: Class<SearchService>
    get() = SearchService::class.java

  fun search(query: String): Single<JsonElement> {
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