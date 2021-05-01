package io.github.ovso.globaltrend.view.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.databinding.ActivityTrendDetailBinding
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.utils.RxBusEvent
import io.github.ovso.globaltrend.view.base.viewBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.jsoup.nodes.Element
import timber.log.Timber

class TrendDetailActivity : AppCompatActivity() {
  private val binding by viewBinding(ActivityTrendDetailBinding::inflate)
  private val compositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    RxBusBehavior.listen(RxBusEvent.OnTrendItemClick::class.java)
      .subscribe {
        Timber.d(it.toString())
        setupItem(it.item)
      }.addTo(compositeDisposable)
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
//    findViewById<TextView>(R.id.tv_trend_detail_all).text = elementsByTag?.first()?.toString()
    binding.tvTrendDetailAll.text = elementsByTag?.first()?.toString()
//    binding.tvTrendDetailAll.text =


    //ht:news_item_title
    //ht:news_item_snippet // description
    //ht:news_item_url     // 출처웹주소.\ㅋ
    //ht:news_item_source // 출처
  }

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.clear()
  }
}
