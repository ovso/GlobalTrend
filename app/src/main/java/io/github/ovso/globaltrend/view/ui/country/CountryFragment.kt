package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.FragmentCountryBinding
import io.github.ovso.globaltrend.view.adapter.CountryAdapter
import kotlinx.android.synthetic.main.fragment_country.recyclerview_country

class CountryFragment : Fragment() {

  private lateinit var viewModel: CountryViewModel
  private val adapter = CountryAdapter()

  companion object {
    fun newInstance() = CountryFragment()
  }

  @Suppress("UNCHECKED_CAST")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) =
        CountryViewModel(requireContext()) as T
    }).get(CountryViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val inflate = DataBindingUtil.inflate<FragmentCountryBinding>(
      inflater,
      R.layout.fragment_country,
      container,
      false
    )
    inflate.viewModel = viewModel
    return inflate.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setHasOptionsMenu(true)
    activity?.title = getString(R.string.country_title)
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
    recyclerview_country.adapter = adapter
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    activity?.finish()
    return super.onOptionsItemSelected(item)
  }
}