package com.abishov.hexocat.common.rule;

import android.app.Activity;
import android.text.TextUtils;
import com.squareup.spoon.SpoonRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class CaptureScreenshotTestRuleCallback implements ActivityTestRuleCallback {

  private final SpoonRule spoonRule;
  private CaptureScreenshot captureScreenshot;
  private String testMethodName;

  public CaptureScreenshotTestRuleCallback() {
    spoonRule = new SpoonRule();
  }

  @Override
  public Statement apply(Statement base, Description description) {
    captureScreenshot = description.getAnnotation(CaptureScreenshot.class);
    testMethodName = description.getMethodName();

    return spoonRule.apply(base, description);
  }

  @Override
  public void afterActivityLaunched(Activity activity) {
    if (captureScreenshot != null) {
      captureScreenshot(activity, "before_", captureScreenshot.before());
    }
  }

  @Override
  public void afterActivityFinished(Activity activity) {
    if (captureScreenshot != null) {
      captureScreenshot(activity, "after_", captureScreenshot.after());
    }
  }

  private void captureScreenshot(Activity activity, String prefix, String tag) {
    if (TextUtils.isEmpty(tag)) {
      tag = prefix + testMethodName;
    }

    spoonRule.screenshot(activity, tag);
  }
}
