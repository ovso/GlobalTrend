package io.github.ovso.globaltrend.view.ui.detail

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.globaltrend.databinding.ItemTrendDetailBinding
import io.github.ovso.globaltrend.databinding.ItemTrendDetailFooter2Binding
import io.github.ovso.globaltrend.extension.toHtml
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.view.ui.web.WebActivity
import io.github.ovso.globaltrend.view.ui.web.WebViewModel
import org.jsoup.nodes.Element
import java.util.*
import javax.inject.Inject

class TrendDetailFooterAdapter @Inject constructor() :
  RecyclerView.Adapter<TrendDetailFooterAdapter.TrendDetailFooterViewHolder>() {

  var keyword: String? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendDetailFooterViewHolder {
    return TrendDetailFooterViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: TrendDetailFooterViewHolder, position: Int) {
    holder.onBindViewHolder()
    holder.keyword = keyword
  }

  override fun getItemCount(): Int = 1

  class TrendDetailFooterViewHolder private constructor(private val binding: ItemTrendDetailFooter2Binding) :
    RecyclerView.ViewHolder(binding.root) {
    var keyword: String? = null
    fun onBindViewHolder() {
      for (x in 0 until binding.root.childCount) {
        binding.root.getChildAt(x).setOnClickListener {
          (it as? TextView)?.text?.toString()?.let { site ->
            when (site.toLowerCase(Locale.getDefault())) {
              "Google".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://google.com/search?q=",
                keyword = keyword
              )
              "Daum".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://search.daum.net/search?q=",
                keyword = keyword
              )
              "Naver".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://search.naver.com/search.naver?query=",
                keyword = keyword
              )
              "Zum".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://search.zum.com/search.zum?query=",
                keyword = keyword
              )
              "YouTube".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://www.youtube.com/results?search_query=",
                keyword = keyword
              )
              "Yahoo".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://search.yahoo.com/search?p=",
                keyword = keyword
              )
              "Bing".toLowerCase(Locale.getDefault()) -> navigateToWeb(
                url = "https://www.bing.com/search?q=",
                keyword = keyword
              )
              else -> {
              }
            }
          }
        }
      }
/*
      binding.ivTrendDetailItemGoogle.setOnClickListener {
        navigateToWeb(
          url = "https://google.com/search?q=",
          keyword = keyword
        )

      }
      binding.ivTrendDetailItemDaum.setOnClickListener {
        navigateToWeb(
          url = "https://search.daum.net/search?q=",
          keyword = keyword
        )
      }
      binding.ivTrendDetailItemNaver.setOnClickListener {
        navigateToWeb(
          url = "https://search.naver.com/search.naver?query=",
          keyword = keyword
        )
      }
      binding.ivTrendDetailItemYoutube.setOnClickListener {
        navigateToWeb(
          url = "https://www.youtube.com/results?search_query=",
          keyword = keyword
        )
      }
*/
    }

    private fun navigateToWeb(url: String, keyword: String?) {
/*
      RxBusBehavior.publish(
        WebViewModel.RxBusWeb(
          keyword,
          "$url$keyword"
        )
      )
      itemView.context.startActivity(Intent(itemView.context, WebActivity::class.java))
*/

      Intent(Intent.ACTION_VIEW, Uri.parse("$url$keyword")).apply {
        itemView.context.startActivity(this)
      }
    }

    companion object {
      fun create(parent: ViewGroup): TrendDetailFooterViewHolder {
        return TrendDetailFooterViewHolder(
          ItemTrendDetailFooter2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
      }
    }

  }

}

class TrendDetailAdapter @Inject constructor() :
  ListAdapter<Element, TrendDetailAdapter.TrendDetailViewHolder>(DIFF_UTIL) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendDetailViewHolder {
    return TrendDetailViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: TrendDetailViewHolder, position: Int) {
    holder.onBindViewHolder(getItem(position))
  }


  class TrendDetailViewHolder private constructor(private val binding: ItemTrendDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindViewHolder(item: Element) {
      binding.tvTrendDetailDesc.text =
        item.getElementsByTag("ht:news_item_snippet")?.text().toHtml()
      binding.tvTrendDetailSource.text = item.getElementsByTag("ht:news_item_source")?.text()
      itemView.setOnClickListener {
        RxBusBehavior.publish(
          WebViewModel.RxBusWeb(
            title = item.getElementsByTag("ht:news_item_title")?.text(),
            url = item.getElementsByTag("ht:news_item_url")?.text(),
          )
        )
        Intent(it.context, WebActivity::class.java).apply {
          it.context.startActivity(this)
        }
      }
    }

    companion object {
      fun create(parent: ViewGroup): TrendDetailViewHolder {
        return TrendDetailViewHolder(
          ItemTrendDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
      }
    }
  }

  companion object {
    val DIFF_UTIL = object : DiffUtil.ItemCallback<Element>() {
      override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
        return areItemsTheSame(oldItem, newItem)
      }
    }
  }
}
