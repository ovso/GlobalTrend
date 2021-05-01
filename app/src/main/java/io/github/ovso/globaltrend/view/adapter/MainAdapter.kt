package io.github.ovso.globaltrend.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.utils.RxBusEvent
import io.github.ovso.globaltrend.view.adapter.MainAdapter.MainViewHolder
import io.github.ovso.globaltrend.view.ui.detail.TrendDetailActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_trend.*
import kotlinx.android.synthetic.main.item_trend.view.*
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
  var elements: Elements? = null

  var clickListener: ((Element?) -> Unit)? = null

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = MainViewHolder.create(parent)

  override fun getItemCount() = elements?.size ?: 0

  override fun onBindViewHolder(
    holder: MainViewHolder,
    position: Int
  ) {
    holder.onBindViewHolder(elements?.get(position))
    holder.itemView.textview_item_rank.text = "${position.inc()}"
  }

  class MainViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(
    containerView!!
  ), LayoutContainer {
    private var element: Element? = null

    companion object {
      fun create(parent: ViewGroup): MainViewHolder {
        return MainViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_trend, parent, false)
        )
      }
    }

    fun onBindViewHolder(element: Element?) {
      this.element = element
      textview_item_title.text = title
      textview_item_traffic.text = traffic
      imageview_item_thumb.load(imageUrl)

      itemView.setOnClickListener {
        RxBusBehavior.publish(RxBusEvent.OnTrendItemClick(element))
        it.context.startActivity(Intent(it.context, TrendDetailActivity::class.java))
      }

//      App.rxBus2.send(WebViewModel.RxBusWeb(title, "https://google.com/search?q=$title"))
//      it.context.startActivity(Intent(it.context, WebActivity::class.java))


    }

    private val imageUrl: String?
      get() = element?.getElementsByTag("ht:picture")?.text()
    private val title: String?
      get() = element?.getElementsByTag("title")?.text()
    private val traffic: String?
      get() = element?.getElementsByTag("ht:approx_traffic")?.text()
  }

  class RxBusElement(val element: Element)
}
