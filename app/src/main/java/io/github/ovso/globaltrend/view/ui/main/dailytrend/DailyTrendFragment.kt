package io.github.ovso.globaltrend.view.ui.main.dailytrend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentDailyTrendBinding
import io.github.ovso.globaltrend.view.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_daily_trend.recyclerview_daily_trend

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
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) =
        DailyTrendViewModel(requireContext()) as T
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
    setupRev()
    obRevListData()

    viewModel.fetchList()
  }

  private fun obRevListData() {
    viewModel.elementsLiveData.observe(this, Observer {
      adapter.elements = it
      adapter.notifyDataSetChanged()
    })
  }

  private fun setupRev() {
    recyclerview_daily_trend.adapter = adapter
  }
}