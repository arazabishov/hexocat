package com.abishov.hexocat.common.views

import androidx.fragment.app.Fragment
import com.abishov.hexocat.Hexocat

abstract class BaseFragment : Fragment() {

  override fun onDestroyView() {
    super.onDestroyView()

    (requireActivity().applicationContext as Hexocat)
      .refWatcher().watch(this)
  }
}
