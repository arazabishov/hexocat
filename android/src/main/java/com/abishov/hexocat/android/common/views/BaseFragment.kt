package com.abishov.hexocat.android.common.views

import androidx.fragment.app.Fragment
import com.abishov.hexocat.android.Hexocat

abstract class BaseFragment : Fragment() {

  override fun onDestroyView() {
    super.onDestroyView()

    (requireActivity().applicationContext as Hexocat)
      .refWatcher().watch(this)
  }
}
