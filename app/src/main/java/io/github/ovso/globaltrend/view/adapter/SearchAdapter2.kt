package io.github.ovso.globaltrend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.api.model.Item
import io.github.ovso.globaltrend.extension.startActivity
import io.github.ovso.globaltrend.view.adapter.SearchAdapter2.SearchViewHolder
import io.github.ovso.globaltrend.view.ui.web.WebActivity
import io.github.ovso.globaltrend.view.ui.web.WebViewModel.RxBusWeb
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search.imageview_search_item
import kotlinx.android.synthetic.main.item_search.textview_search_item_title

class SearchAdapter2() : PagedListAdapter<Item, SearchViewHolder>(DIFF_CALLBACK) {

  companion object {
    private val DIFF_CALLBACK = object : ItemCallback<Item>() {
      override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
      }

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
      }
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = SearchViewHolder.create(parent)

  override fun onBindViewHolder(
    holder: SearchViewHolder,
    position: Int
  ) = holder.bind(getItem(position)!!)

  class SearchViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(
    containerView!!
  ), LayoutContainer {
    private lateinit var item: Item

    companion object {
      fun create(parent: ViewGroup): SearchViewHolder {
        return SearchViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        )
      }
    }

    fun bind(item: Item) {
      this.item = item
      textview_search_item_title.text = title
      Glide.with(itemView).load(imageUrl).into(imageview_search_item)
      itemView.setOnClickListener {
        App.rxBus2.send(RxBusWeb(title, item.formattedUrl))
        it.startActivity(WebActivity::class.java)
      }
    }

    private val imageUrl: String?
      get() = item.pagemap?.cse_thumbnail?.first()?.src
    private val title: String?
      get() = item.title
  }

}