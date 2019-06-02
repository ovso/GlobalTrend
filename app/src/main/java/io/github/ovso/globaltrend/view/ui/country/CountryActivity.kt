package io.github.ovso.globaltrend.view.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.ovso.globaltrend.R

class CountryActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_country)
    if (savedInstanceState == null) {
      supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_fragment_container, CountryFragment.newInstance())
        .commitNow()
    }
  }
}