package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

    viewModel.elementsLiveData.observe(this, Observer {

    })

    viewModel.fetchList()
  }

}