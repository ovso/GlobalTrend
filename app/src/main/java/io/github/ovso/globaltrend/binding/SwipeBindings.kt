package io.github.ovso.globaltrend.binding

import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object SwipeBindings {
  @JvmStatic
  @BindingAdapter("colorSchemeResources")
  fun setColorSchemeresource(view: SwipeRefreshLayout, @ColorRes color: Int) {
    view.setColorSchemeResources(color)
  }
}
