package io.github.ovso.globaltrend.view.adapter

import android.app.Activity
import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.adapter.CountryAdapter.CountryViewHolder
import io.github.ovso.globaltrend.view.ui.main.MainViewModel.RxBusCountryIndex
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_country.imageview_country_item_flag
import kotlinx.android.synthetic.main.item_country.imageview_country_item_thumb
import kotlinx.android.synthetic.main.item_country.textview_country_item_cname
import kotlinx.android.synthetic.main.item_country.textview_country_item_title
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class CountryAdapter : RecyclerView.Adapter<CountryViewHolder>() {
  var elements: Elements? = null
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = CountryViewHolder.create(parent)

  override fun getItemCount() = elements?.size ?: 0

  override fun onBindViewHolder(
    holder: CountryViewHolder,
    position: Int
  ) {
    holder.bind(elements!![position])
  }

  class CountryViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(
    containerView!!
  ), LayoutContainer {
    private lateinit var element: Element

    companion object {
      fun create(parent: ViewGroup): CountryViewHolder {
        return CountryViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        )
      }
    }

    fun bind(element: Element) {
      this.element = element
      Glide.with(itemView).load(flagImgUrl).into(imageview_country_item_flag)
      Glide.with(itemView).load(trendImgUrl).into(imageview_country_item_thumb)
      textview_country_item_cname.text = countryName
      textview_country_item_title.text = title
      itemView.setOnClickListener {
        //        App.rxBus2.send(RxBusWeb(title, "https://google.co.kr/search?q=$title"))
//        it.startActivity(WebActivity::class.java)
        val originalIndex = res.getStringArray(R.array.country_codes).indexOf(countryCode)
        App.rxBus.send(RxBusCountryIndex(originalIndex))
        if (it.context is Activity) (it.context as Activity).finish()
      }
    }

    private val trendImgUrl: String
      get() = element.getElementsByTag("ht:picture").text()

    private val flagImgUrl: String
      get() = res.getStringArray(R.array.country_flags)[countryIndex]

    private val title: String
      get() = element.getElementsByTag("title").text()

    private val countryName: String
      get() = res.getStringArray(R.array.country_names)[countryIndex]

    private val countryIndex: Int
      get() = res.getStringArray(R.array.country_codes).indexOf(countryCode)

    private val countryCode: String
      get() = Uri.parse(element.getElementsByTag("link").text()).getQueryParameter("geo")!!

    private val res: Resources
      get() = itemView.resources

  }
}