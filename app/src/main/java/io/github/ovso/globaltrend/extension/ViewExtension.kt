package io.github.ovso.globaltrend.extension

import android.view.View

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.inVisible() {
  visibility = View.INVISIBLE
}

fun View.gone() {
  visibility = View.GONE
}