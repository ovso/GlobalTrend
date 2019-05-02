package io.github.ovso.globaltrend.binding

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter

object WebViewBinding {
  @JvmStatic
  @BindingAdapter("webViewClient")
  fun setWebViewClient(webview: WebView, webviewClient: WebViewClient) {
    webview.webViewClient = webviewClient
    val settings = webview.settings
    settings.javaScriptEnabled = true
    settings.setAppCacheEnabled(true)
    settings.cacheMode = WebSettings.LOAD_NO_CACHE
  }

  @JvmStatic
  @BindingAdapter("webChromeClient")
  fun setWebViewClient(webview: WebView, webChromeClient: WebChromeClient) {
    webview.webChromeClient = webChromeClient
  }

  @JvmStatic
  @BindingAdapter("loadUrl")
  fun loadUrl(webview: WebView, url: String) {
    webview.loadUrl(url)
  }

  @JvmStatic
  @BindingAdapter("showAndHide")
  fun showAndHideProgressbar(
    progressbar: ContentLoadingProgressBar,
    isLoading: Boolean = false
  ) {
    when (isLoading) {
      true -> progressbar.show()
      else -> progressbar.hide()
    }
  }
}