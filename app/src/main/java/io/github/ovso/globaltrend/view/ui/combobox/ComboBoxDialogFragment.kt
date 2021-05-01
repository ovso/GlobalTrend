package io.github.ovso.globaltrend.view.ui.combobox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.ovso.globaltrend.databinding.FragmentComboBoxBinding

class ComboBoxDialogFragment : BottomSheetDialogFragment() {

  private var binding: FragmentComboBoxBinding? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentComboBoxBinding.inflate(LayoutInflater.from(context))
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  companion object {
    fun newInstance(): ComboBoxDialogFragment =
      ComboBoxDialogFragment().apply {
      }

  }
}
