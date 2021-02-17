package com.abishov.hexocat.android.home.trending

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.abishov.hexocat.android.R
import com.abishov.hexocat.android.common.espresso.BottomNavigationViewMatcher
import com.abishov.hexocat.android.home.repository.RepositoryItemRobot

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
        composeTestRule.onNode(hasSubstring(error), useUnmergedTree = true)
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
