package io.github.ovso.globaltrend.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentSearchBinding
import io.github.ovso.globaltrend.view.adapter.SearchAdapter
import kotlinx.android.synthetic.main.fragment_search.recyclerview_search

class SearchFragment : Fragment() {
  private val adapter = SearchAdapter()

  companion object {
    fun newInstance() = SearchFragment()
  }

  private lateinit var viewModel: SearchViewModel

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

  private fun inflateDataBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentSearchBinding {
    return DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_search,
      container,
      false
    )
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupRev()
    obRevListData()

    viewModel.titleLiveData.observe(this, Observer {
      activity?.title = it
    })

  }

  private fun obRevListData() {
    viewModel.itemsLiveData.observe(this, Observer {
      val positionStart = adapter.items.size - 1
      val itemCount = it.size
      adapter.items.addAll(it)
      adapter.notifyItemRangeInserted(positionStart, itemCount)
    })
  }

  private fun setupRev() {
    recyclerview_search.adapter = adapter
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): SearchViewModel {
    return ViewModelProviders.of(this, AndroidViewModelFactory.getInstance(activity?.application!!))
      .get(SearchViewModel::class.java)
  }

}
