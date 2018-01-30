package com.abishov.hexocat.common.espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import org.hamcrest.Matcher;

public final class CustomConstraintViewAction implements ViewAction {

  private final ViewAction viewAction;
  private final Matcher<View> constraints;

  public static ViewAction withCustomConstraints(ViewAction action, Matcher<View> constraints) {
    return new CustomConstraintViewAction(action, constraints);
  }

  private CustomConstraintViewAction(ViewAction viewAction, Matcher<View> constraints) {
    this.viewAction = viewAction;
    this.constraints = constraints;
  }

  @Override
  public Matcher<View> getConstraints() {
    return constraints;
  }

  @Override
  public String getDescription() {
    return viewAction.getDescription();
  }

  @Override
  public void perform(UiController uiController, View view) {
    viewAction.perform(uiController, view);
  }
}
