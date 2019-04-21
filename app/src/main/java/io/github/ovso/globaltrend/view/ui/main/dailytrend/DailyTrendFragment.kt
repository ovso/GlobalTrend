package io.github.ovso.globaltrend.view.ui.main.dailytrend

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.ovso.globaltrend.R

class DailyTrendFragment : Fragment() {

  companion object {
    fun newInstance() = DailyTrendFragment()
  }

  private lateinit var viewModel: DailyTrendViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    return inflater.inflate(R.layout.fragment_daily_trend, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this)
      .get(DailyTrendViewModel::class.java)
    // TODO: Use the ViewModel
  }

}