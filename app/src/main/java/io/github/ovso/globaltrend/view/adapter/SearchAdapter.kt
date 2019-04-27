package io.github.ovso.globaltrend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.adapter.SearchAdapter.DetailViewHolder
import io.github.ovso.globaltrend.view.ui.detail.DetailActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_trend.imageview_item_thumb
import kotlinx.android.synthetic.main.item_trend.textview_item_title
import kotlinx.android.synthetic.main.item_trend.textview_item_traffic
import kotlinx.android.synthetic.main.item_trend.view.textview_item_rank
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class SearchAdapter : RecyclerView.Adapter<DetailViewHolder>() {
  var elements: Elements? = null
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = DetailViewHolder.create(parent)

  override fun getItemCount() = elements?.size ?: 0

  override fun onBindViewHolder(
    holder: DetailViewHolder,
    position: Int
  ) {
    holder.bind(elements!![position])
    holder.itemView.textview_item_rank.text = "${position.inc()}"
  }

  class DetailViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(
      containerView!!
  ), LayoutContainer {
    private lateinit var element: Element

    companion object {
      fun create(parent: ViewGroup): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trend, parent, false)
        )
      }
    }

    fun bind(element: Element) {
      this.element = element
      textview_item_title.text = title
      textview_item_traffic.text = traffic
      Glide.with(imageview_item_thumb)
          .load(imageUrl)
          .into(imageview_item_thumb)
      itemView.setOnClickListener {
        App.rxBus2.send(this.element)
        DetailActivity.start(itemView.context)
      }
    }

    private val imageUrl: String
      get() = element.getElementsByTag("ht:picture").text()
    private val title: String
      get() = element.getElementsByTag("ht:news_item_title").text()
    private val traffic: String
      get() = element.getElementsByTag("ht:approx_traffic").text()
    private val pictureSource
    get() = element.getElementsByTag("ht:picture_source").text()
  }
}