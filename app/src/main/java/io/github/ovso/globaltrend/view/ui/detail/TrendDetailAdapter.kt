package io.github.ovso.globaltrend.view.ui.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.globaltrend.databinding.ItemTrendDetailBinding
import io.github.ovso.globaltrend.databinding.ItemTrendDetailFooterBinding
import io.github.ovso.globaltrend.extension.toHtml
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.view.ui.web.WebActivity
import io.github.ovso.globaltrend.view.ui.web.WebViewModel
import org.jsoup.nodes.Element
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

  class TrendDetailFooterViewHolder private constructor(private val binding: ItemTrendDetailFooterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var keyword: String? = null
    fun onBindViewHolder() {
      binding.ivTrendDetailItemGoogle.setOnClickListener {

      }
      binding.ivTrendDetailItemDaum.setOnClickListener {

      }
      binding.ivTrendDetailItemNaver.setOnClickListener {

      }
    }

    companion object {
      fun create(parent: ViewGroup): TrendDetailFooterViewHolder {
        return TrendDetailFooterViewHolder(
          ItemTrendDetailFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
