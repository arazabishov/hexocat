package com.abishov.hexocat.home.trending

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import com.abishov.hexocat.R
import com.abishov.hexocat.common.espresso.BottomNavigationViewMatcher
import com.abishov.hexocat.common.espresso.CustomConstraintViewAction.Companion.withCustomConstraints
import com.abishov.hexocat.home.repository.RepositoryItemRobot
import org.hamcrest.CoreMatchers.allOf

fun trendingScreen(func: TrendingRobot.() -> Unit): TrendingRobot {
  try {
    onView(withId(R.id.item_trending))
      .check(matches(BottomNavigationViewMatcher.isNavigationItemChecked))
  } catch (assertionError: AssertionError) {
    onView(withId(R.id.item_trending))
      .perform(click())
      .check(matches(BottomNavigationViewMatcher.isNavigationItemChecked))
  }

  return TrendingRobot().apply { func() }
}

class TrendingRobot {

  fun retry() {
    onView(allOf(isDisplayed(), withId(R.id.button_retry)))
      .perform(click())
  }

  fun swipeLeft() {
    onView(withId(R.id.viewpager_trending))
      .perform(withCustomConstraints(ViewActions.swipeLeft(),
        isDisplayingAtLeast(80)))
  }

  fun errorMessage(error: String) {
    onView(allOf(withId(R.id.textview_error), isDisplayed()))
      .check(matches(withText(error)))
  }

  fun retryButtonIsVisible() {
    onView(allOf(isDisplayed(), withId(R.id.button_retry)))
      .check(matches(isDisplayed()))
  }

  fun repositoryAt(
    position: Int, func: RepositoryItemRobot.() -> Unit
  ): RepositoryItemRobot {
    return RepositoryItemRobot(
      allOf(withId(R.id.recyclerview_trending), isDisplayed()), position
    ).apply { func() }
  }
}
