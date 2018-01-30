package com.abishov.hexocat.common.rule;

import android.app.Activity;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public interface ActivityTestRuleCallback extends TestRule {

  @Override
  default Statement apply(Statement base, Description description) {
    return base;
  }

  default void afterActivityLaunched(Activity activity) {
  }

  default void afterActivityFinished(Activity activity) {
  }

  default void beforeActivityLaunched(Activity activity) {
  }
}
