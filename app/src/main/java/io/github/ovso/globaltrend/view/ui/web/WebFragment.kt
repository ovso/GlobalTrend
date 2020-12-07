package io.github.ovso.globaltrend.view.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import androidx.lifecycle.observe
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentWebBinding
import io.github.ovso.globaltrend.extension.loadAdaptiveBanner
import io.github.ovso.globaltrend.view.base.DataBindingFragment
import kotlinx.android.synthetic.main.fragment_web.*

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
    loadAdaptiveBanner(binding.ffAllBannerContainer, getString(R.string.ads_banner_unit_id))
  }

  private fun setupWebViewNavi() {
    val viewModel = binding.viewModel!!

    binding.includeWebNav.buttonWebNavBack.setOnClickListener { webview_web.goBack() }
    binding.includeWebNav.buttonWebNavForw.setOnClickListener { webview_web.goForward() }
    binding.includeWebNav.buttonWebNavRefresh.setOnClickListener {
      viewModel.urlObField.set(
        webview_web.url
      )
    }
    binding.includeWebNav.buttonWebNavShare.setOnClickListener {
      startActivity(
        viewModel.shareIntent(
          binding.webviewWeb.url!!
        )
      )
    }
    binding.includeWebNav.buttonWebNavBrowser.setOnClickListener {
      startActivity(
        viewModel.browserIntent(
          binding.webviewWeb.url!!
        )
      )
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun setupWebView() {
    webview_web.settings.run {
      javaScriptEnabled = true
      cacheMode = WebSettings.LOAD_NO_CACHE
    }
  }

  private fun setupTitle() {
    val viewModel = binding.viewModel!!
    val owner = viewLifecycleOwner
    viewModel.titleLiveData.observe(owner) {
      activity?.title = it
    }
  }

  override fun onBackPressed() {
    if (webview_web.canGoBack()) {
      webview_web.goBack()
    } else {
      activity?.finish()
    }
  }
}
