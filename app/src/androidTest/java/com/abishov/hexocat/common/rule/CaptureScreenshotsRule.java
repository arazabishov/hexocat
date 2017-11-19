package com.abishov.hexocat.common.rule;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.text.TextUtils;
import com.squareup.spoon.SpoonRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class CaptureScreenshotsRule<T extends Activity> extends ActivityTestRule<T> {

  private final SpoonRule spoonRule;
  private CaptureScreenshots captureScreenshots;
  private String testMethodName;

  public CaptureScreenshotsRule(Class<T> activity) {
    super(activity);
    spoonRule = new SpoonRule();
  }

  public CaptureScreenshotsRule(Class<T> activity, boolean initialTouchMode) {
    super(activity, initialTouchMode);
    spoonRule = new SpoonRule();
  }

  public CaptureScreenshotsRule(Class<T> activity, boolean initialTouchMode, boolean launchActivity) {
    super(activity, initialTouchMode, launchActivity);
    spoonRule = new SpoonRule();
  }

  @Override
  public Statement apply(Statement base, Description description) {
    testMethodName = description.getMethodName();
    captureScreenshots = description.getAnnotation(CaptureScreenshots.class);

    Statement statement = spoonRule.apply(base, description);
    return super.apply(statement, description);
  }

  @Override
  protected void afterActivityLaunched() {
    if (captureScreenshots != null) {
      String tag = captureScreenshots.before();
      if (TextUtils.isEmpty(tag)) {
        tag = "before_" + testMethodName;
      }
      screenshot(tag);
    }
  }

  @Override
  protected void afterActivityFinished() {
    if (captureScreenshots != null) {
      String tag = captureScreenshots.after();
      if (TextUtils.isEmpty(tag)) {
        tag = "after_" + testMethodName;
      }
      screenshot(tag);
    }
  }

  public void screenshot(String tag) {
    if (getActivity() == null) {
      throw new IllegalStateException(
          "Activity has not been started yet" + " or has already been killed!");
    }

    spoonRule.screenshot(getActivity(), tag);
  }
}