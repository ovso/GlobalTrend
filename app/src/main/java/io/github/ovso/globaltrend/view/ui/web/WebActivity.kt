package io.github.ovso.globaltrend.view.ui.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.ovso.globaltrend.R

class WebActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_web)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, WebFragment.newInstance())
        .commitNow()
    }
  }

}
