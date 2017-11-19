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

  CaptureScreenshotsRule(Class<T> activity, boolean initialTouchMode, boolean launchActivity) {
    super(activity, initialTouchMode, launchActivity);
    spoonRule = new SpoonRule();
  }

  public static <T extends Activity> Builder<T> builder(Class<T> activity) {
    return new Builder<>(activity);
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

  public static class Builder<T extends Activity> {

    private final Class<T> activity;
    private boolean initialTouchMode;
    private boolean launchActivity;

    Builder(Class<T> activity) {
      this.activity = activity;
    }

    public Builder<T> initialTouchMode() {
      this.initialTouchMode = true;
      return this;
    }

    public Builder<T> launchActivity() {
      this.launchActivity = true;
      return this;
    }

    public CaptureScreenshotsRule<T> build() {
      return new CaptureScreenshotsRule<T>(activity, initialTouchMode, launchActivity);
    }
  }
}