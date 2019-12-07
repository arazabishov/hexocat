package com.abishov.hexocat.common.views

interface Presenter<in T : View> {
  fun onAttach(view: T)

  fun onDetach()
}
