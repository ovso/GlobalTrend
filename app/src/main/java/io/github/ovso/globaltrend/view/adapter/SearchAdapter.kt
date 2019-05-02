package io.github.ovso.globaltrend.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.api.model.Item
import io.github.ovso.globaltrend.extension.startActivity
import io.github.ovso.globaltrend.view.adapter.SearchAdapter.DetailViewHolder
import io.github.ovso.globaltrend.view.ui.web.WebActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search.imageview_search_item
import kotlinx.android.synthetic.main.item_search.textview_search_item_title

class SearchAdapter : RecyclerView.Adapter<DetailViewHolder>() {
  var items: List<Item>? = null
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = DetailViewHolder.create(parent)

  override fun getItemCount() = items?.size ?: 0

  override fun onBindViewHolder(
    holder: DetailViewHolder,
    position: Int
  ) {
    holder.bind(items!![position])
  }

  class DetailViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(
    containerView!!
  ), LayoutContainer {
    private lateinit var item: Item

    companion object {
      fun create(parent: ViewGroup): DetailViewHolder {
        return DetailViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        )
      }
    }

    fun bind(item: Item) {
      this.item = item
      textview_search_item_title.text = title
      Glide.with(itemView).load(imageUrl).into(imageview_search_item)
      itemView.setOnClickListener {
        it.startActivity(WebActivity::class.java)
      }
    }

    private val imageUrl: String?
      get() = item.pagemap?.cse_thumbnail?.first()?.src
    private val title: String?
      get() = item.title
  }
}