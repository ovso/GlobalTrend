package io.github.ovso.globaltrend.view.ui.main.realtimetrend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentRealTimeTrendBinding
import io.github.ovso.globaltrend.view.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_real_time_trend.recyclerview_real_time

class RealTimeTrendFragment : Fragment() {
    private val adapter = MainAdapter()

    companion object {
        fun newInstance() = RealTimeTrendFragment()
    }

    private lateinit var viewModel: RealTimeTrendViewModel

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        viewModel = provideViewModel()
        val binding = inflateDataBinding(inflater, container)
        binding.viewModel = viewModel
        return binding.root
    }

    private fun inflateDataBinding(
      inflater: LayoutInflater,
      container: ViewGroup?
    ): FragmentRealTimeTrendBinding {
        return DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_real_time_trend,
            container,
            false
        )
    }

    private fun provideViewModel() =
        ViewModelProviders.of(this).get(RealTimeTrendViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRev()
        obRevListData()
        // viewModel.fetchList()
    }

    private fun obRevListData() {
        viewModel.elementsLiveData.observe(this, Observer {
            adapter.elements = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupRev() {
        recyclerview_real_time.adapter = adapter
    }
}
