package io.github.ovso.globaltrend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.extension.startActivity
import io.github.ovso.globaltrend.view.adapter.MainAdapter.MainViewHolder
import io.github.ovso.globaltrend.view.ui.web.WebActivity
import io.github.ovso.globaltrend.view.ui.web.WebViewModel.RxBusWeb
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_trend.imageview_item_thumb
import kotlinx.android.synthetic.main.item_trend.textview_item_title
import kotlinx.android.synthetic.main.item_trend.textview_item_traffic
import kotlinx.android.synthetic.main.item_trend.view.textview_item_rank
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
  var elements: Elements? = null
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = MainViewHolder.create(parent)

  override fun getItemCount() = elements?.size ?: 0

  override fun onBindViewHolder(
    holder: MainViewHolder,
    position: Int
  ) {
    holder.bind(elements!![position])
    holder.itemView.textview_item_rank.text = "${position.inc()}"
  }

  class MainViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(
    containerView!!
  ), LayoutContainer {
    private lateinit var element: Element

    companion object {
      fun create(parent: ViewGroup): MainViewHolder {
        return MainViewHolder(
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
        App.rxBus2.send(RxBusWeb(title, "https://google.co.kr/search?q=$title"))
        it.startActivity(WebActivity::class.java)
      }
    }

    private val imageUrl: String
      get() = element.getElementsByTag("ht:picture").text()
    private val title: String
      get() = element.getElementsByTag("title").text()
    private val traffic: String
      get() = element.getElementsByTag("ht:approx_traffic").text()
  }

  class RxBusElement(val element: Element) {

  }
}