package io.github.ovso.globaltrend.view.ui.main.realtimetrend

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.ovso.globaltrend.R

class RealTimeTrendFragment : Fragment() {

  companion object {
    fun newInstance() = RealTimeTrendFragment()
  }

  private lateinit var viewModel: RealTimeTrendViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_real_time_trend, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(RealTimeTrendViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
