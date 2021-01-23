package com.abishov.hexocat.home.repository

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
      matcher = hasAnySibling(hasText(name)).and(hasText(text)),
      useUnmergedTree = true
    ).assertExists()
  }

  fun owner(name: String) = withText(name)

  fun description(description: String) = withText(description)

  fun languages(vararg languages: String) = languages.forEach { withText(it) }

  fun topics(vararg topics: String) = topics.forEach { withText("#$it") }

  fun clickOnRow() {
    composeTestRule.onNodeWithText(
      text = name, ignoreCase = false, useUnmergedTree = true
    ).onParent().performClick()
  }
}
