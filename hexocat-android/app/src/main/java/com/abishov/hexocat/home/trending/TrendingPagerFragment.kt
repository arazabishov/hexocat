package com.abishov.hexocat.home.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abishov.hexocat.R
import com.abishov.hexocat.common.views.BaseFragment

fun create(): TrendingPagerFragment {
  return TrendingPagerFragment()
}

class TrendingPagerFragment : BaseFragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ) = inflater.inflate(R.layout.trending_pager_view, container, false)

  // @SuppressFBWarnings("BC_UNCONFIRMED_CAST")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val trendingPagerView = view as TrendingPagerView

    trendingPagerView.setAdapter(
      TrendingPagerAdapter(context!!, childFragmentManager)
    )
  }
}
