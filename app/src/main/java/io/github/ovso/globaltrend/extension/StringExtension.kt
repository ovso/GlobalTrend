package io.github.ovso.globaltrend.extension

fun String.replaceLessThan10(number: Int) = when (number < 10) {
  true -> "0$number"
  false -> "$number"
}