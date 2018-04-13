package com.abishov.hexocat.home.trending

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import butterknife.BindView
import butterknife.ButterKnife
import com.abishov.hexocat.R

class TrendingPagerView(context: Context, attrs: AttributeSet) : CoordinatorLayout(context, attrs) {

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
