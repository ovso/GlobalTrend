package io.github.ovso.globaltrend.view.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.github.ovso.globaltrend.App
import io.github.ovso.globaltrend.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_detail.fab
import kotlinx.android.synthetic.main.activity_detail.toolbar
import org.jsoup.nodes.Element

class DetailActivity : AppCompatActivity() {

  private val compositeDisposable: CompositeDisposable = CompositeDisposable()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null)
        .show()
    }

    toRxBusObservable()
  }

  private fun toRxBusObservable() {
    addDisposable(
      App.rxBus2.toObservable()
        .subscribeBy { any ->
          setupTitle((any as? Element))
        }
    )
  }

  private fun setupTitle(it: Element?) {
    title = it?.getElementsByTag("title")
      ?.text()
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }

  fun addDisposable(d: Disposable) {
    compositeDisposable.add(d)
  }

  fun clearDisposable() {
    compositeDisposable.clear()
  }

  override fun onDestroy() {
    clearDisposable()
    super.onDestroy()
  }

  companion object {
    fun start(context: Context) {
      val intent = Intent(context, DetailActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      context.startActivity(intent)
    }
  }
}
