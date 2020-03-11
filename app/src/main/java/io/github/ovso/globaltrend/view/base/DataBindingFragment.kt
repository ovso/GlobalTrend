package io.github.ovso.globaltrend.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import io.github.ovso.globaltrend.BR

abstract class DataBindingFragment<T : ViewDataBinding>(
  @LayoutRes private val layoutResId: Int,
  private val viewModelCls: Class<out ViewModel>
) : Fragment() {

  protected lateinit var binding: T

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val owner = this@DataBindingFragment

    with(binding) {
      setVariable(BR._all, ViewModelProviders.of(owner).get(viewModelCls))
      lifecycleOwner = owner
      executePendingBindings()
    }
  }
}
