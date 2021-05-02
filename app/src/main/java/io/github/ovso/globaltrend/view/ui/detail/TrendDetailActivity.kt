package io.github.ovso.globaltrend.view.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.globaltrend.databinding.ActivityTrendDetailBinding
import io.github.ovso.globaltrend.extension.toHtml
import io.github.ovso.globaltrend.view.base.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class TrendDetailActivity : AppCompatActivity() {
  private val binding by viewBinding(ActivityTrendDetailBinding::inflate)
  private val viewModel by viewModels<TrendDetailViewModel>()

  @Inject
  lateinit var itemsAdapter: TrendDetailAdapter

  @Inject
  lateinit var footerAdapter: TrendDetailFooterAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    setupRv()
    setupOb()
  }

  private fun setupRv() {
    with(binding.rvTrendDetail) {
      addItemDecoration(DividerItemDecoration(this.context, RecyclerView.VERTICAL))
      adapter = ConcatAdapter(itemsAdapter, footerAdapter)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }

  private fun setupOb() {
    val owner = this
    viewModel.thumb.asLiveData().observe(owner) {
      binding.ivTrendDetail.load(it) {
        transformations(RoundedCornersTransformation(10f))
      }
    }
    viewModel.items.asLiveData().observe(owner) {
      if (it.isNullOrEmpty().not()) {
        itemsAdapter.submitList(it)
        footerAdapter.notifyDataSetChanged()
      }
    }
    viewModel.title.asLiveData().observe(owner) {
      title = it.toHtml()
      footerAdapter.apply {
        keyword = it
        notifyDataSetChanged()
      }
    }
  }
}
