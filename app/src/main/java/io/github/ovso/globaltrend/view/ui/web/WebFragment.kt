package io.github.ovso.globaltrend.view.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentWebBinding
import kotlinx.android.synthetic.main.fragment_web.webview_web
import kotlinx.android.synthetic.main.layout_web_navigation.button_web_nav_back
import kotlinx.android.synthetic.main.layout_web_navigation.button_web_nav_browser
import kotlinx.android.synthetic.main.layout_web_navigation.button_web_nav_forw
import kotlinx.android.synthetic.main.layout_web_navigation.button_web_nav_refresh
import kotlinx.android.synthetic.main.layout_web_navigation.button_web_nav_share

class WebFragment : Fragment() {

  companion object {
    fun newInstance() = WebFragment()
  }

  private lateinit var viewModel: WebViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding =
      DataBindingUtil.inflate<FragmentWebBinding>(inflater, R.layout.fragment_web, container, false)
    viewModel = ViewModelProviders.of(this).get(WebViewModel::class.java)
    binding.viewModel = viewModel
    return binding.root
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
    button_web_nav_refresh.setOnClickListener { viewModel.urlObField.set(webview_web.url) }
    button_web_nav_share.setOnClickListener { }
    button_web_nav_browser.setOnClickListener { }
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
    viewModel.titleLiveData.observe(this, Observer {
      activity?.title = it
    })

  }
}
