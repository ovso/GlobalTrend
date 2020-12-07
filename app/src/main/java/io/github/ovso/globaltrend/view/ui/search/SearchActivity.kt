package io.github.ovso.globaltrend.view.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.globaltrend.R
import kotlinx.android.synthetic.main.activity_search.toolbar

class SearchActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, SearchFragment.newInstance())
        .commitNow()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    finish()
    return super.onOptionsItemSelected(item)
  }

  companion object {
    fun start(context: Context) {
      val intent = Intent(context, SearchActivity::class.java)
      context.startActivity(intent)
    }
  }
}
