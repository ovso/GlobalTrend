package io.github.ovso.globaltrend.view.ui.dailytrend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentDailyTrendBinding
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
    val binding = inflateDataBinding(inflater, container)
    viewModel = provideViewModel()
    binding.viewModel = viewModel
    return binding.root
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): DailyTrendViewModel {
    return ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
          DailyTrendViewModel(requireContext().applicationContext) as T
      })
      .get(DailyTrendViewModel::class.java)
  }

  private fun inflateDataBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentDailyTrendBinding {
    return DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_daily_trend,
      container,
      false
    )
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupTitle()
    setupRev()
    obRevListData()
    viewModel.fetchList()
  }

  private fun setupTitle() {
    viewModel.titleLiveData.observe(viewLifecycleOwner, Observer {
      activity?.title = "$it ${getString(R.string.main_title_suffix)}"
    })
  }

  private fun obRevListData() {
    viewModel.elementsLiveData.observe(viewLifecycleOwner, Observer {
      adapter.elements = it
      adapter.notifyDataSetChanged()
    })
  }

  private fun setupRev() {
    recyclerview_daily_trend.adapter = adapter
  }
}
