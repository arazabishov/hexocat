package com.abishov.hexocat.home;

import static io.appflate.restmock.RESTMockServer.whenGET;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import com.abishov.hexocat.common.rule.CaptureScreenshots;
import com.abishov.hexocat.common.rule.CaptureScreenshotsRule;
import io.appflate.restmock.RESTMockServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class HomeScreenTest {

  @Rule
  public final ActivityTestRule<HomeActivity> activityRule =
      CaptureScreenshotsRule.builder(HomeActivity.class)
          .initialTouchMode()
          .build();

  private HomeRobot homeRobot;

  @Before
  public void setUp() throws Exception {
    RESTMockServer.reset();
    homeRobot = new HomeRobot();
  }

  @Test
  @CaptureScreenshots
  public void clickOnTrendingTabMustNavigateToTrendingScreen() {
    whenGET(pathContains("search/repositories"))
        .thenReturnFile("search/repositories/200_trending_1.json");

    activityRule.launchActivity(new Intent());
    homeRobot.navigateToTrendingScreen().someAction();
  }
}
