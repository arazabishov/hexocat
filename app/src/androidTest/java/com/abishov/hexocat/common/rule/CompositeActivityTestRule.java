package com.abishov.hexocat.common.rule;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class CompositeActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

  private final List<ActivityTestRuleCallback> callbacks;

  private CompositeActivityTestRule(Class<T> activityClass, boolean initialTouchMode,
      boolean launchActivity, List<ActivityTestRuleCallback> callbacks) {
    super(activityClass, initialTouchMode, launchActivity);
    this.callbacks = callbacks;
  }

  public static <T extends Activity> Builder<T> builder(Class<T> activityClass) {
    return new Builder<T>(activityClass);
  }

  @Override
  public Statement apply(Statement base, Description description) {
    for (ActivityTestRuleCallback callback : callbacks) {
      base = callback.apply(base, description);
    }

    return super.apply(base, description);
  }

  @Override
  protected void beforeActivityLaunched() {
    super.beforeActivityLaunched();

    for (ActivityTestRuleCallback callback : callbacks) {
      callback.beforeActivityLaunched(getActivity());
    }
  }

  @Override
  protected void afterActivityLaunched() {
    super.afterActivityLaunched();

    for (ActivityTestRuleCallback callback : callbacks) {
      callback.afterActivityLaunched(getActivity());
    }
  }

  @Override
  protected void afterActivityFinished() {
    super.afterActivityFinished();

    for (ActivityTestRuleCallback callback : callbacks) {
      callback.afterActivityFinished(getActivity());
    }
  }

  public static final class Builder<T extends Activity> {

    private final Class<T> activityClass;
    private final List<ActivityTestRuleCallback> callbacks;
    private boolean initialTouchMode;
    private boolean launchActivity;

    private Builder(Class<T> activityClass) {
      this.activityClass = activityClass;
      this.callbacks = new ArrayList<>();
    }

    public Builder<T> initialTouchMode() {
      this.initialTouchMode = true;
      return this;
    }

    public Builder<T> launchActivity() {
      this.launchActivity = true;
      return this;
    }

    public Builder<T> add(ActivityTestRuleCallback activityTestRuleCallback) {
      Objects.requireNonNull(activityTestRuleCallback);

      callbacks.add(activityTestRuleCallback);
      return this;
    }

    public ActivityTestRule<T> build() {
      return new CompositeActivityTestRule<T>(activityClass, initialTouchMode, launchActivity,
          callbacks);
    }
  }
}
