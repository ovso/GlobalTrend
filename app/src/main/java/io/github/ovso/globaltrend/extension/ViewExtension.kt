package io.github.ovso.globaltrend.extension

import android.content.Intent
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

fun View.startActivity(`class`: Class<*>) {
  val intent = Intent(context, `class`)
  context.startActivity(intent)
}