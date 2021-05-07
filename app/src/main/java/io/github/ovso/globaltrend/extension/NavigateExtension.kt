package io.github.ovso.globaltrend.extension

import android.content.Context
import android.content.Intent
import android.net.Uri


fun Context.navigateToChrome(url: String?) {
  Intent(
    Intent.ACTION_VIEW,
    Uri.parse(url ?: "")
  ).apply {
    setPackage("com.android.chrome")
    try {
      startActivity(this)
    } catch (e: Exception) {
      navigateToExternalWeb(url)
    }
  }
}

private fun Context.navigateToExternalWeb(url: String?) {
  Intent(
    Intent.ACTION_VIEW,
    Uri.parse(url ?: "")
  ).apply {
    startActivity(this)
  }
}
