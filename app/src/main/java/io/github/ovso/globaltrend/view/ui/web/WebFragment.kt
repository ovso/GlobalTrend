package io.github.ovso.globaltrend.view.ui.web

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentWebBinding
import kotlinx.android.synthetic.main.fragment_web.progressbar_web

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
    viewModel.titleLiveData.observe(this, Observer {
      activity?.title = it
    })

    progressbar_web.hide()
  }

}
