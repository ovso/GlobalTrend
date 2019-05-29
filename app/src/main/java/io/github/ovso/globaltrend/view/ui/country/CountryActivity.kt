package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {

  private lateinit var viewModel: CountryViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = provideViewModel()
    val inflate =
      DataBindingUtil.setContentView<ActivityCountryBinding>(this, R.layout.activity_country)
    inflate.viewModel = viewModel


    viewModel.getObservables()
  }

  @Suppress("UNCHECKED_CAST")
  private fun provideViewModel() =
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>) =
        CountryViewModel(context = applicationContext) as T
    }).get(CountryViewModel::class.java)
}