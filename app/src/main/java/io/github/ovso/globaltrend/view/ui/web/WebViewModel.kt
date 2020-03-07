package io.github.ovso.globaltrend.view.ui.web

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.ovso.globaltrend.App
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class WebViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val titleLiveData = MutableLiveData<String>()
    val urlObField = ObservableField<String>()
    val progressObField = ObservableField<Int>()
    val isLoadingObField = ObservableField<Boolean>()
    val canGoBackObField = ObservableBoolean()
    val canGoForwObField = ObservableBoolean()

    val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            isLoadingObField.set(true)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            isLoadingObField.set(false)
            view?.let {
                canGoBackObField.set(it.canGoBack())
                canGoForwObField.set(it.canGoForward())
            }
        }
    }
    val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressObField.set(newProgress)
        }
    }

    init {
        toRxBusObservable()
    }

    private fun toRxBusObservable() {
        addDisposable(
            App.rxBus2.toObservable().subscribeBy(onNext = { any ->
                (any as? RxBusWeb)?.let {
                    titleLiveData.value = it.title
                    urlObField.set(it.url)
                }
            }, onError = {

            })
        )
    }

    private fun addDisposable(d: Disposable) {
        compositeDisposable.add(d)
    }

    private fun clearDisposable() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposable()
    }

    fun shareIntent(url: String): Intent =
        Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }, titleLiveData.value)

    fun browserIntent(url: String): Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    class RxBusWeb(var title: String?, var url: String?)

}
