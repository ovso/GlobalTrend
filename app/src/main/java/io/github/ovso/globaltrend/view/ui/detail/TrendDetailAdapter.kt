package io.github.ovso.globaltrend.view.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.globaltrend.databinding.ItemTrendDetailBinding
import io.github.ovso.globaltrend.databinding.ItemTrendDetailFooter2Binding
import io.github.ovso.globaltrend.extension.navigateToChrome
import io.github.ovso.globaltrend.extension.toHtml
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
            val context = itemView.context
            when (site.toLowerCase(Locale.getDefault())) {
              "Google".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://google.com/search?q=$keyword")
              "Daum".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://search.daum.net/search?q=$keyword")
              "Naver".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://search.naver.com/search.naver?query=$keyword")
              "Zum".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://search.zum.com/search.zum?query=$keyword")
              "YouTube".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://www.youtube.com/results?search_query=$keyword")
              "Yahoo".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://search.yahoo.com/search?p=$keyword")
              "Bing".toLowerCase(Locale.getDefault()) ->
                context.navigateToChrome("https://www.bing.com/search?q=$keyword")
            }
          }
        }
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
        itemView.context.navigateToChrome(
          url = item.getElementsByTag("ht:news_item_url")?.text()
        )
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
