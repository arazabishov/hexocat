package com.abishov.hexocat.home.trending

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.ui.test.*
import com.abishov.hexocat.R
import com.abishov.hexocat.common.espresso.BottomNavigationViewMatcher
import com.abishov.hexocat.home.repository.RepositoryItemRobot

fun trendingScreen(
  composeTestRule: ComposeTestRule,
  context: Context,
  func: TrendingRobot.() -> Unit
): TrendingRobot {
  try {
    onView(withId(R.id.item_trending))
      .check(matches(BottomNavigationViewMatcher.isNavigationItemChecked))
  } catch (assertionError: AssertionError) {
    onView(withId(R.id.item_trending))
      .perform(click())
      .check(matches(BottomNavigationViewMatcher.isNavigationItemChecked))
  }

  return TrendingRobot(composeTestRule, context).apply { func() }
}

class TrendingRobot(
  private val composeTestRule: ComposeTestRule,
  private val context: Context
) {
  private fun retryButton(): SemanticsNodeInteraction {
    return composeTestRule.onNodeWithText(
      text = context.getString(R.string.home_action_retry),
      useUnmergedTree = true
    )
  }

  fun retry() {
    retryButton().assertIsDisplayed()
      .performClick()
  }

  fun errorMessage(error: String) {
    onNode(hasSubstring(error), useUnmergedTree = true)
      .assertIsDisplayed()
  }

  fun retryButtonIsVisible() {
    retryButton().assertIsDisplayed()
  }

  fun withRepository(
    repositoryName: String,
    func: RepositoryItemRobot.() -> Unit
  ): RepositoryItemRobot {
    return RepositoryItemRobot(
      composeTestRule = composeTestRule,
      name = repositoryName
    ).apply { func() }
  }
}
