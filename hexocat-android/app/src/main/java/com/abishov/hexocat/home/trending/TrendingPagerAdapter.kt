package com.abishov.hexocat.home.trending

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.abishov.hexocat.R
import java.util.*

internal class TrendingPagerAdapter(
  context: Context, fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

  private val periods: List<Period>

  init {
    periods = Arrays.asList(
      Period(context.getString(R.string.trending_today), 1),
      Period(context.getString(R.string.trending_last_week), 7),
      Period(context.getString(R.string.trending_last_month), 30)
    )
  }

  override fun getItem(position: Int): Fragment {
    return create(periods[position].days)
  }

  override fun getCount(): Int {
    return periods.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return periods[position].label
  }

  internal data class Period(val label: String, val days: Int)
}
