package com.abishov.hexocat.home;

import static io.appflate.restmock.RESTMockServer.whenGET;
import static io.appflate.restmock.RequestsVerifier.verifyGET;
import static io.appflate.restmock.utils.RequestMatchers.hasExactQueryParameters;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.CoreMatchers.allOf;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import com.abishov.hexocat.R;
import com.abishov.hexocat.common.rule.CaptureScreenshots;
import com.abishov.hexocat.common.rule.CaptureScreenshotsRule;
import com.abishov.hexocat.common.rule.MockWebServerRule;
import com.abishov.hexocat.home.trending.TrendingRobot;
import io.appflate.restmock.utils.QueryParam;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class HomeScreenTest {

  @Rule
  public final ActivityTestRule<HomeActivity> activityRule =
      CaptureScreenshotsRule.builder(HomeActivity.class)
          .initialTouchMode()
          .build();

  @Rule
  public final MockWebServerRule mockWebServerRule = new MockWebServerRule();

  private HomeRobot homeRobot;

  @Before
  public void setUp() throws Exception {
    homeRobot = new HomeRobot();
  }

  @Test
  @CaptureScreenshots
  public void mustRenderTrendingRepositoriesForToday() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_today.json");

    activityRule.launchActivity(new Intent());

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_today);

    trendingRobot.withRepositoryItemAt(0)
        .withName("charts")
        .withStars(8760)
        .withForks(263)
        .withDescription("frappe — Responsive, modern SVG Charts with zero dependencies");
    trendingRobot.withRepositoryItemAt(1)
        .withName("state-of-the-art-result-for-machine-learning-problems")
        .withStars(4238)
        .withForks(597)
        .withDescription("RedditSota — This repository provides state of the art "
            + "(SoTA) results for all machine learning problems.");

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-23"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-07-31"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).never();
  }

  @Test
  public void mustRenderTrendingRepositoriesForWeek() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-23"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_week.json");

    activityRule.launchActivity(new Intent());

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_last_week);

    trendingRobot.withRepositoryItemAt(0)
        .withName("hangzhouYunQi2017ppt")
        .withStars(3998)
        .withForks(1649)
        .withDescription("Alibaba-Technology");
    trendingRobot.withRepositoryItemAt(1)
        .withName("bottery")
        .withStars(3131)
        .withForks(143)
        .withDescription("google");

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-23"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-07-31"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);
  }

  @Test
  public void mustRenderTrendingRepositoriesForMonth() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-07-31"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_month.json");

    activityRule.launchActivity(new Intent());

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_last_month);

    trendingRobot.withRepositoryItemAt(0)
        .withName("deepo")
        .withStars(2699)
        .withForks(163)
        .withDescription("ufoym — A Docker image containing almost all popular"
            + " deep learning frameworks.");
    trendingRobot.withRepositoryItemAt(1)
        .withName("nba-go")
        .withStars(2575)
        .withForks(118)
        .withDescription("xxhomey19 — The finest NBA CLI.");

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-23"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-07-31"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(1);
  }

  @Test
  public void mustRenderRetryButtoOnTimeout() {
    // TODO: matching on Toasts (see amazon examples)
  }

  @Test
  public void mustRenderRetryButtonOnIoException() {
  }

  @Test
  public void mustRequestBackendAfterRetryButtonClicked() {
  }

  @Test
  public void mustNavigateToBrowserOnRepositoryItemClicked() {
  }

  // mustShowNoConnectionMessageOn404
  // mustShowNoConnectionMessageOn500
  // checkPullToRefreshBehaviour
}
