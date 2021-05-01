package io.github.ovso.globaltrend.view.ui.detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.utils.RxBus
import io.github.ovso.globaltrend.utils.RxBusBehavior
import io.github.ovso.globaltrend.utils.RxBusEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class TrendDetailActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_trend_detail)

    RxBusBehavior.listen(RxBusEvent.OnTrendItemClick::class.java)
      .subscribe {
        findViewById<ImageView>(R.id.iv_trend_detail).load(
          it.item?.getElementsByTag("ht:picture")?.text()
        )
      }.addTo(CompositeDisposable())
  }
}
