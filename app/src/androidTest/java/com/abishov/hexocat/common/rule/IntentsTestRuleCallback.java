package com.abishov.hexocat.common.rule;

import android.app.Activity;
import android.support.test.espresso.intent.Intents;

public final class IntentsTestRuleCallback implements ActivityTestRuleCallback {

  @Override
  public void afterActivityLaunched(Activity activity) {
    Intents.init();
  }

  @Override
  public void afterActivityFinished(Activity activity) {
    Intents.release();
  }
}
