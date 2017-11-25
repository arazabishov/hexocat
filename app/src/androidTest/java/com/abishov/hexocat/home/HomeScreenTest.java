package com.abishov.hexocat.home;

import static io.appflate.restmock.RESTMockServer.whenGET;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import com.abishov.hexocat.common.rule.CaptureScreenshots;
import com.abishov.hexocat.common.rule.CaptureScreenshotsRule;
import com.abishov.hexocat.home.trending.TrendingRobot;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;
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
  public void mustRenderTrendingRepositoriesForToday() {
    whenGET(pathContains("search/repositories"))
        .thenReturnFile("search/repositories/200_trending_today.json");

    activityRule.launchActivity(new Intent());

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen();

    trendingRobot.withRepositoryItemAt(0)
        .withName("charts");
    trendingRobot.withRepositoryItemAt(1)
        .withName("state-of-the-art-result-for-machine-learning-problems");
    trendingRobot.withRepositoryItemAt(2)
        .withName("hangzhouYunQi2017ppt");
    trendingRobot.withRepositoryItemAt(3)
        .withName("bottery");
    trendingRobot.withRepositoryItemAt(4)
        .withName("deepo");

    // TODO: refactor TrendingPagerView to load only one page at a time
    RequestsVerifier.verifyGET(pathContains("search/repositories")).atLeast(1);
  }
}
