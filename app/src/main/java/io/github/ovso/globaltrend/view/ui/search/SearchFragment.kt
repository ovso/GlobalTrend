package io.github.ovso.globaltrend.view.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R

class SearchFragment : Fragment() {

  companion object {
    fun newInstance() = SearchFragment()
  }

  private lateinit var viewModel: SearchViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.fragment_search, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = provideViewModel()
    viewModel.titleLiveData.observe(this, Observer {
      activity?.title = it
    })
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel(): SearchViewModel {
    return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(requireContext().applicationContext) as T
      }
    })
      .get(SearchViewModel::class.java)
  }

}
