package io.github.ovso.globaltrend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.adapter.MainAdapter.MainViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_trend.*
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
  var elements: Elements? = null
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder.create(parent)

  override fun getItemCount() = elements?.size ?: 0

  override fun onBindViewHolder(
    holder: MainViewHolder,
    position: Int
  ) {
    holder.bind(elements!![position])
  }

  class MainViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {

    companion object {
      fun create(parent: ViewGroup): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_trend, parent, false))
      }
    }

    fun bind(element: Element) {
      textview_item_rank.text = element.getElementsByTag("title").text()
    }

  }
}