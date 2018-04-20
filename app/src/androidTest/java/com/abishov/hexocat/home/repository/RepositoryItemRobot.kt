package com.abishov.hexocat.home.repository

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.LinearLayout
import com.abishov.hexocat.R
import com.abishov.hexocat.common.espresso.DrawableMatcher.withCompoundDrawable
import com.abishov.hexocat.common.espresso.RecyclerViewMatcher.atPosition
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matcher

class RepositoryItemRobot(
  private val recyclerViewMatcher: Matcher<View>,
  private val position: Int
) {

  fun name(name: String) {
    matchesAtPosition(
      withChild(
        allOf(
          withId(R.id.textview_repository_name), withText(name)
        )
      )
    )
  }

  fun description(description: String) {
    matchesAtPosition(
      withChild(
        allOf(
          withId(R.id.textview_repository_description),
          withText(description)
        )
      )
    )
  }

  fun stars(stars: Int) {
    matchesAtPosition(
      withChild(
        allOf(
          instanceOf(LinearLayout::class.java),
          withChild(
            allOf(
              withParent(instanceOf(LinearLayout::class.java)),
              withId(R.id.textview_repository_stars),
              withText(stars.toString()),
              withCompoundDrawable(R.drawable.ic_star)
            )
          )
        )
      )
    )
  }

  fun forks(forks: Int) {
    matchesAtPosition(
      withChild(
        allOf(
          instanceOf(LinearLayout::class.java),
          withChild(
            allOf(
              withId(R.id.textview_repository_forks),
              withText(forks.toString()),
              withCompoundDrawable(R.drawable.ic_fork)
            )
          )
        )
      )
    )
  }

  fun clickOnRow() {
    onView(recyclerViewMatcher)
      .perform(actionOnItemAtPosition<ViewHolder>(position, click()))
  }

  private fun matchesAtPosition(matcher: Matcher<View>) {
    onView(recyclerViewMatcher)
      .perform(scrollToPosition<ViewHolder>(position))
      .check(
        matches(
          atPosition(
            position, allOf(
              instanceOf(RepositoryItemView::class.java), matcher
            )
          )
        )
      )
  }
}
