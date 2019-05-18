package io.github.ovso.globaltrend.api

import io.github.ovso.globaltrend.api.model.CustomSearch
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
  @GET("/customsearch/v1?key=AIzaSyCjo-Wbm9mCDwORSXuKhOdjamrFGZ-Rw0Y&cx=012722901045059265659:m8q8x8ftuii")
  fun search(@Query("q") query: String, @Query("start") start: Int): Single<CustomSearch>

}