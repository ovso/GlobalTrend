package io.github.ovso.globaltrend.view.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import io.github.ovso.globaltrend.utils.AppOpenManager
import io.github.ovso.globaltrend.view.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showOpeningAd()
  }

  private fun showOpeningAd() {
    with(AppOpenManager(this.application)) {
      callback = { ad ->
        ad?.fullScreenContentCallback = object : FullScreenContentCallback() {
          override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            launchMain()
          }

          override fun onAdDismissedFullScreenContent() {
            launchMain()
          }
        }
        ad?.show(this@SplashActivity)
      }
      fetchAd()
    }

  }

  private fun launchMain() {
    ActivityCompat.finishAffinity(this)
    startActivity(Intent(this, MainActivity::class.java))
  }

  override fun onBackPressed() {
    // super.onBackPressed()
  }
}
