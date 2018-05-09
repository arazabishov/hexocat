package com.abishov.hexocat.common.views

import android.support.v4.app.Fragment
import com.abishov.hexocat.Hexocat

abstract class BaseFragment : Fragment() {

  override fun onDestroyView() {
    super.onDestroyView()

    (activity!!.applicationContext as Hexocat)
      .refWatcher().watch(this)
  }
}
