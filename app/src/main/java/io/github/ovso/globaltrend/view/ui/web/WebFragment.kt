package io.github.ovso.globaltrend.view.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import androidx.lifecycle.Observer
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentWebBinding
import io.github.ovso.globaltrend.view.base.DataBindingFragment
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.layout_web_navigation.*

class WebFragment :
  DataBindingFragment<FragmentWebBinding>(R.layout.fragment_web, WebViewModel::class.java),
  OnBackPressedListener {

  companion object {
    fun newInstance() = WebFragment()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupTitle()
    setupWebView()
    setupWebViewNavi()
  }

  private fun setupWebViewNavi() {
    button_web_nav_back.setOnClickListener { webview_web.goBack() }
    button_web_nav_forw.setOnClickListener { webview_web.goForward() }
    button_web_nav_refresh.setOnClickListener { binding.viewModel.urlObField.set(webview_web.url) }
    button_web_nav_share.setOnClickListener {
      startActivity(
        binding.viewModel.shareIntent(
          webview_web.url
        )
      )
    }
    button_web_nav_browser.setOnClickListener {
      startActivity(
        binding.viewModel.browserIntent(
          webview_web.url
        )
      )
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setupWebView() {
    webview_web.settings.run {
      javaScriptEnabled = true
      setAppCacheEnabled(true)
      cacheMode = WebSettings.LOAD_NO_CACHE
    }
  }

  private fun setupTitle() {
    binding.viewModel.titleLiveData.observe(this, Observer {
      activity?.title = it
    })
  }

  override fun onBackPressed() {
    if (webview_web.canGoBack()) {
      webview_web.goBack()
    } else {
      activity?.finish()
    }
  }
}
