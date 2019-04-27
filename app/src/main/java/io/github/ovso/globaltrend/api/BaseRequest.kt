package io.github.ovso.globaltrend.api

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import io.github.ovso.globaltrend.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap
import java.util.Locale
import java.util.concurrent.TimeUnit

abstract class BaseRequest<T> {
  val api: T
    get() = createRetrofit().create(apiClass)

  private val language: String
    get() = locale.language

  private val country: String
    get() = locale.country

  private val locale: Locale
    get() = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)

  protected abstract val apiClass: Class<T>

  private val baseUrl: String
    get() = EndPoint.BASE_URL.value

  protected val isInterceptor: Boolean
    get() = BuildConfig.DEBUG

  private fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(createClient())
        .build()
  }

  private fun createClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.readTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
    httpClient.connectTimeout(TIMEOUT_SECONDS.toLong(), TimeUnit.SECONDS)
    httpClient.addInterceptor { chain ->
      val original = chain.request()
      val requestBuilder = original.newBuilder()
      var headers: MutableMap<String, String>? = createHeaders()
      if (headers != null) {
        addHeader(requestBuilder, headers)
      } else {
        headers = HashMap()
        addHeader(requestBuilder, headers)
      }
      val request = requestBuilder.build()
      chain.proceed(request)
    }
    if (isInterceptor) {
      val interceptor = HttpLoggingInterceptor()
      interceptor.level = HttpLoggingInterceptor.Level.BODY
      httpClient.addInterceptor(interceptor)
    }
    return httpClient.build()
  }

  private fun addHeader(
    requestBuilder: Request.Builder,
    headers: Map<String, String>
  ) {
    for ((key, value) in headers) {
      requestBuilder.addHeader(key, value)
    }
  }

  protected fun createHeaders(): MutableMap<String, String> {
    return HashMap()
  }

  //https://www.googleapis.com/customsearch/v1?key=AIzaSyAM9aaztXVlGNX40ZoFV5MYvpmg65qOCbQ&cx=012722901045059265659:m8q8x8ftuii&q=%ED%8C%A8%EC%8A%A4%ED%8A%B8%ED%8A%B8%EB%9E%99
  private enum class EndPoint(val value: String) {
    BASE_URL("https://www.googleapis.com/customsearch/v1")
  }

  companion object {
    private const val TIMEOUT_SECONDS = 7
  }
}