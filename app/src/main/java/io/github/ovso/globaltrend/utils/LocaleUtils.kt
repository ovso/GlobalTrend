package io.github.ovso.globaltrend.utils

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.util.Locale

object LocaleUtils {
  val language: String
    get() = locale.getLanguage()
  val country: String
    get() = locale.getCountry()
  private val locale: Locale
    get() = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0)
}