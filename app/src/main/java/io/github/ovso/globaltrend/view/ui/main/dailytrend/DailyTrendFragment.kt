package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_daily_trend.*

class DailyTrendFragment : Fragment() {
  private val adapter: MainAdapter = MainAdapter()

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
      adapter.elements = it
      recyclerview_daily_trend.adapter = adapter
    })
    viewModel.refreshLiveData.observe(this, Observer {
      refreshlayout_daily_trend.isRefreshing = it
    })
    refreshlayout_daily_trend.setOnRefreshListener {
      clearRev()
      viewModel.fetchList()
    }

    viewModel.fetchList()
  }

  fun clearRev() {
    adapter.elements = null
    adapter.notifyDataSetChanged()
  }

}