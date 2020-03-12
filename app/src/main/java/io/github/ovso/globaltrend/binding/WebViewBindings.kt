package io.github.ovso.globaltrend.binding

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("webViewClient")
fun setWebViewClient(webview: WebView, webviewClient: WebViewClient) {
  webview.webViewClient = webviewClient
}

@BindingAdapter("webChromeClient")
fun setWebViewClient(webview: WebView, webChromeClient: WebChromeClient) {
  webview.webChromeClient = webChromeClient
}

@BindingAdapter("loadUrl")
fun loadUrl(webview: WebView, url: String) {
  webview.loadUrl(url)
}

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
