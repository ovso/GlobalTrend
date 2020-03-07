package io.github.ovso.globaltrend.view.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.globaltrend.view.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

  override fun onBackPressed() {
    // super.onBackPressed()
  }
}
