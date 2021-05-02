package io.github.ovso.globaltrend.view.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import coil.load
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import io.github.ovso.globaltrend.databinding.ActivityTrendDetailBinding
import io.github.ovso.globaltrend.view.base.viewBinding
import org.jsoup.nodes.Element
import javax.inject.Inject

@AndroidEntryPoint
class TrendDetailActivity : AppCompatActivity() {
  private val binding by viewBinding(ActivityTrendDetailBinding::inflate)
  private val viewModel by viewModels<TrendDetailViewModel>()

  @Inject
  lateinit var adapter: TrendDetailAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    setupOb()
    setupRv()
  }

  private fun setupRv() {
    with(binding.rvTrendDetail) {
      adapter = this@TrendDetailActivity.adapter
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }

  private fun setupOb() {
    val owner = this
    viewModel.thumb.asLiveData().observe(owner) {
      binding.ivTrendDetail.load(it)
    }
    viewModel.desc.asLiveData().observe(owner) {
      binding.tvTrendDetailDesc.text = it
    }
    viewModel.items.asLiveData().observe(owner) {
      if (it.isNullOrEmpty().not()) adapter.submitList(it)
    }
    viewModel.title.asLiveData().observe(owner) {
      title = it
    }
  }

  private fun setupItem(item: Element?) {
/*
    with(binding.includeTrendDetailItem) {
      textviewItemRank.text = "1"
      this.imageviewItemThumb.load(item?.getElementsByTag("ht:picture")?.text())
      this.textviewItemTitle.text = item?.getElementsByTag("title")?.text()
      this.textviewItemTraffic.text = item?.getElementsByTag("ht:approx_traffic")?.text()
    }
*/
//    binding.tvTrendDetailItemDesc.text = item?.getElementsByTag("ht:news_item")?
    val elementsByTag = item?.getElementsByTag("ht:news_item")
    Logger.d("elementsByTag: ${elementsByTag?.first()}")

    val sb = StringBuilder().apply {
      append(item?.getElementsByTag("pubDate")?.text())
      append(
        item?.getElementsByTag("ht:news_item")?.first()?.getElementsByTag("ht:news_item_snippet")
          ?.text()
      )

    }

    Logger.d("sb: $sb")

    //ht:news_item_title
    //ht:news_item_snippet // description
    //ht:news_item_url     // 출처웹주소.\ㅋ
    //ht:news_item_source // 출처
  }
}
