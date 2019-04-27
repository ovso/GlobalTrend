package io.github.ovso.globaltrend.api

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
  @GET("key=AIzaSyAM9aaztXVlGNX40ZoFV5MYvpmg65qOCbQ&cx=012722901045059265659:m8q8x8ftuii")
  fun search(@Query("q") query: String): Single<JsonElement>
}