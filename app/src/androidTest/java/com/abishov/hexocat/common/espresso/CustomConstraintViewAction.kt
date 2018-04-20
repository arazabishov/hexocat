package com.abishov.hexocat.common.espresso

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher

class CustomConstraintViewAction private constructor(
  private val viewAction: ViewAction,
  private val constraints: Matcher<View>
) : ViewAction {

  override fun getConstraints(): Matcher<View> {
    return constraints
  }

  override fun getDescription(): String {
    return viewAction.description
  }

  override fun perform(uiController: UiController, view: View) {
    viewAction.perform(uiController, view)
  }

  companion object {
    fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction {
      return CustomConstraintViewAction(action, constraints)
    }
  }
}
