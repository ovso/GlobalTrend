package io.github.ovso.globaltrend.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.adapter.MainAdapter.MainViewHolder
import io.github.ovso.globaltrend.view.ui.web.WebActivity
import io.github.ovso.globaltrend.view.ui.web.WebViewModel.RxBusWeb
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_trend.*
import kotlinx.android.synthetic.main.item_trend.view.*
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

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
    holder.onBindViewHolder(elements!![position])
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

    fun onBindViewHolder(element: Element) {
      this.element = element
      textview_item_title.text = title
      textview_item_traffic.text = traffic
      Glide.with(imageview_item_thumb)
        .load(imageUrl)
        .into(imageview_item_thumb)
      itemView.setOnClickListener {
        with(AlertDialog.Builder(it.context)) {
          setSingleChoiceItems(arrayOf("즐겨찾기 추가", "검색하기"), 0) { dialog, which ->
            dialog.dismiss()
            Timber.d("which: $which")
            Toast.makeText(it.context, "which:$which", Toast.LENGTH_SHORT).show()
            when (which) {
              -1 -> {
                App.rxBus2.send(RxBusWeb(title, "https://google.com/search?q=$title"))
                it.context.startActivity(Intent(it.context, WebActivity::class.java))
              }
              else -> {
              }
            }
          }
          show()
        }
      }
    }

    private val imageUrl: String
      get() = element.getElementsByTag("ht:picture").text()
    private val title: String
      get() = element.getElementsByTag("title").text()
    private val traffic: String
      get() = element.getElementsByTag("ht:approx_traffic").text()
  }

  class RxBusElement(val element: Element)
}
