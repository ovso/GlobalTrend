package io.github.ovso.globaltrend.view.ui.dailytrend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentDailyTrendBinding
import io.github.ovso.globaltrend.extension.viewBinding
import io.github.ovso.globaltrend.view.adapter.MainAdapter
import io.github.ovso.globaltrend.view.ui.combobox.ComboBoxDialogFragment
import org.jsoup.nodes.Element

@AndroidEntryPoint
class DailyTrendFragment : Fragment(R.layout.fragment_daily_trend) {

  private val binding by viewBinding(FragmentDailyTrendBinding::bind)
  private val viewModel by viewModels<DailyTrendViewModel>()
  private val adapter: MainAdapter = MainAdapter().apply {
    clickListener = onItemClickListener
  }

  private val onItemClickListener:((Element?) -> Unit) = {
    viewModel.onItemClick(it)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupTitle()
    setupRev()
    obRevListData()
    setupRefresh()

  }

  private fun setupRefresh() {
    with(binding.srlDailyTrend) {
      setColorSchemeResources(R.color.colorPrimary)
      setOnRefreshListener {
        viewModel.onRefresh()
      }
    }
  }

  private fun setupTitle() {
    viewModel.titleStateFlow.asLiveData().observe(viewLifecycleOwner) {
      activity?.title = "$it ${getString(R.string.main_title_suffix)}"
    }
  }

  private fun obRevListData() {
    val owner = viewLifecycleOwner
    viewModel.items.asLiveData().observe(owner) {
      with(adapter) {
        elements = it
        notifyDataSetChanged()
      }
    }

    viewModel.isLoadingFlow.asLiveData().observe(owner) {
      binding.srlDailyTrend.isRefreshing = it
    }
  }

  private fun setupRev() {
    binding.rvDailyTrend.adapter = adapter
  }

  companion object {
    fun newInstance(): DailyTrendFragment = DailyTrendFragment()
  }

}
