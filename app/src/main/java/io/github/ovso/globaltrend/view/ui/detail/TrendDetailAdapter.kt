package io.github.ovso.globaltrend.view.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.globaltrend.databinding.ItemTrendDetailBinding
import org.jsoup.nodes.Element
import javax.inject.Inject

class TrendDetailAdapter @Inject constructor() :
  ListAdapter<Element, TrendDetailAdapter.TrendDetailViewHolder>(DIFF_UTIL) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendDetailViewHolder {
    return TrendDetailViewHolder.create(parent)
  }

  override fun onBindViewHolder(holder: TrendDetailViewHolder, position: Int) {
    holder.onBindViewHolder(getItem(position))
  }


  class TrendDetailViewHolder(private val binding: ItemTrendDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBindViewHolder(item: Element) {
      binding.tvTrendDetailDesc.text = "ddd"
      binding.tvTrendDetailSource.text = "한겨레"
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
