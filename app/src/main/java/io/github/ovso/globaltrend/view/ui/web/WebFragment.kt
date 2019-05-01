package io.github.ovso.globaltrend.view.ui.web

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.ovso.globaltrend.R

class WebFragment : Fragment() {

  companion object {
    fun newInstance() = WebFragment()
  }

  private lateinit var viewModel: WebViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_web, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(WebViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
