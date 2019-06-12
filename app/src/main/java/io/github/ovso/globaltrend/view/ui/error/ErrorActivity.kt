package io.github.ovso.globaltrend.view.ui.error

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.globaltrend.R
import io.github.ovso.globaltrend.view.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_error.textview_error

class ErrorActivity : AppCompatActivity() {
  private val lastActivityIntent by lazy { intent.getParcelableExtra<Intent>(EXTRA_INTENT) }
  private val errorText by lazy { intent.getStringExtra(EXTRA_ERROR_TEXT) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_error)
    textview_error.setOnClickListener {
      startActivity(Intent(this, SplashActivity::class.java))
      finish()
    }
  }

  companion object {
    const val EXTRA_INTENT = "EXTRA_INTENT"
    const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"
  }
}
