package com.abishov.hexocat.android.home.repository

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeTestRule

class RepositoryItemRobot(
  private val composeTestRule: ComposeTestRule,
  private val name: String
) {

  init {
    composeTestRule.onNodeWithText(
      text = name, ignoreCase = false, useUnmergedTree = true
    ).assertExists()
  }

  private fun withText(text: String) {
    composeTestRule.onNode(
      matcher = hasAnySibling(hasText(name)).and(hasText(text)
        .or(hasAnyDescendant(hasText(text)))),
      useUnmergedTree = true
    ).assertExists()
  }

  fun owner(name: String) = withText(name)

  fun description(description: String) = withText(description)

  fun primaryLanguage(language: String) = withText(language)

  fun topics(vararg topics: String) = topics.forEach { withText("#$it") }

  fun stars(stars: Int) = withText(stars.toString())

  fun clickOnRow() {
    composeTestRule.onNodeWithText(
      text = name, ignoreCase = false, useUnmergedTree = true
    ).onParent().performClick()
  }
}
