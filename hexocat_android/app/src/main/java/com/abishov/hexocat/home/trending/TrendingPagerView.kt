package com.abishov.hexocat.home.trending

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.abishov.hexocat.R
import com.google.android.material.tabs.TabLayout

class TrendingPagerView(context: Context, attrs: AttributeSet) : androidx.coordinatorlayout.widget.CoordinatorLayout(context, attrs) {

  @BindView(R.id.tab_layout_trending_periods)
  internal lateinit var trendingTabLayout: TabLayout

  @BindView(R.id.viewpager_trending)
  internal lateinit var trendingViewPager: ViewPager

  override fun onFinishInflate() {
    super.onFinishInflate()
    ButterKnife.bind(this)
  }

  fun setAdapter(pagerAdapter: PagerAdapter) {
    trendingViewPager.adapter = pagerAdapter
    trendingTabLayout.setupWithViewPager(trendingViewPager)
  }
}
