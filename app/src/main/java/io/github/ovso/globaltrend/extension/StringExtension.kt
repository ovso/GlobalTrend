package io.github.ovso.globaltrend.extension

import android.text.Spanned
import androidx.core.text.HtmlCompat

fun String.replaceLessThan10(number: Int) = when (number < 10) {
  true -> "0$number"
  false -> "$number"
}

fun String?.toHtml(): Spanned {
  return HtmlCompat.fromHtml(
    this ?: "",
    HtmlCompat.FROM_HTML_MODE_COMPACT
  )
}
