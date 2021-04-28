package io.github.ovso.globaltrend.view.ui.dailytrend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentDailyTrendBinding
import io.github.ovso.globaltrend.extension.viewBinding
import io.github.ovso.globaltrend.view.adapter.MainAdapter

@AndroidEntryPoint
class DailyTrendFragment : Fragment(R.layout.fragment_daily_trend) {

  private val binding by viewBinding(FragmentDailyTrendBinding::bind)
  private val viewModel by viewModels<DailyTrendViewModel>()
  private val adapter: MainAdapter = MainAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupTitle()
    setupRev()
    obRevListData()
    setupRefresh()
/*
    ComboBoxDialogFragment
      .newInstance()
      .show(childFragmentManager, "ItemListDialogFragment")
*/

  }

  private fun setupRefresh() {
    binding.refreshlayoutDailyTrend.setColorSchemeResources(R.color.colorPrimary)
    binding.refreshlayoutDailyTrend.setOnRefreshListener {
      viewModel.onRefresh()
    }
  }

  /*
        app:colorSchemeResources="@{viewModel.swipeLoadingColor}"
      app:onRefreshListener="@{() -> viewModel.onRefresh()}"
      app:refreshing="@{viewModel.isLoading}"

   */
  private fun setupTitle() {
    viewModel.titleLiveData.observe(viewLifecycleOwner, Observer {
      activity?.title = "$it ${getString(R.string.main_title_suffix)}"
    })
  }

  private fun obRevListData() {
    val owner = viewLifecycleOwner
    viewModel.elementsLiveData.observe(owner, {
      adapter.elements = it
      adapter.notifyDataSetChanged()
    })

    viewModel.isLoadingFlow.asLiveData().observe(owner) {
      binding.refreshlayoutDailyTrend.isRefreshing = it
    }
  }

  private fun setupRev() {
    binding.recyclerviewDailyTrend.adapter = adapter
  }

  companion object {
    fun newInstance() = DailyTrendFragment()
  }

}
