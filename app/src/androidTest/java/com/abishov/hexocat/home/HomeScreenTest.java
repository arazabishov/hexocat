package com.abishov.hexocat.home;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static io.appflate.restmock.RESTMockServer.whenGET;
import static io.appflate.restmock.RequestsVerifier.verifyGET;
import static io.appflate.restmock.utils.RequestMatchers.hasExactQueryParameters;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import com.abishov.hexocat.R;
import com.abishov.hexocat.common.rule.MockWebServerRule;
import com.abishov.hexocat.home.trending.TrendingRobot;
import io.appflate.restmock.utils.QueryParam;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class HomeScreenTest {

  @Rule
  public final IntentsTestRule<HomeActivity> activityTestRule =
      new IntentsTestRule<>(HomeActivity.class, false, false);

  @Rule
  public final MockWebServerRule mockWebServerRule = new MockWebServerRule();

  private HomeRobot homeRobot;

  @Before
  public void setUp() throws Exception {
    homeRobot = new HomeRobot();
  }

  @Test
  public void mustRenderTrendingRepositoriesForToday() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_today.json");

    activityTestRule.launchActivity(new Intent());

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
  public void mustRenderTrendingRepositoriesForWeek() throws InterruptedException {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-23"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_week.json");

    activityTestRule.launchActivity(new Intent());

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen()
        .swipeTrendingPagerLeft();

//    .withTrendingTab(R.string.trending_last_week);
//    trendingRobot.withRetryButtonVisible();
//    trendingRobot.withErrorMessage("HTTP 500 Server Error");

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

    activityTestRule.launchActivity(new Intent());

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
  public void mustRender400ErrorMessage() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnEmpty(400);

    activityTestRule.launchActivity(new Intent());

    homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_today)
        .withErrorMessage("HTTP 400 Client Error")
        .withRetryButtonVisible();

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
  public void mustRender500ErrorMessage() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnEmpty(500);

    activityTestRule.launchActivity(new Intent());

    homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_today)
        .withErrorMessage("HTTP 500 Server Error")
        .withRetryButtonVisible();

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
  public void mustRequestBackendAfterRetryButtonClicked() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnEmpty(400);

    activityTestRule.launchActivity(new Intent());

    homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_today)
        .withErrorMessage("HTTP 400 Client Error")
        .withRetryButtonVisible()
        .retry();

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(2);

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
  public void mustRequestBackendAfterPullToRefresh() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_today.json");

    activityTestRule.launchActivity(new Intent());

    homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_today)
        .pullToRefresh();

    verifyGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).exactly(2);

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
  public void mustNavigateToBrowserOnRepositoryItemClicked() {
    whenGET(allOf(pathContains("search/repositories"),
        hasExactQueryParameters(
            new QueryParam("q", "created:>=2017-08-29"),
            new QueryParam("sort", "watchers"),
            new QueryParam("order", "desc"))
    )).thenReturnFile("response/search/repositories/200_trending_today.json");

    activityTestRule.launchActivity(new Intent());

    intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));

    TrendingRobot trendingRobot = homeRobot.navigateToTrendingScreen()
        .withTrendingTab(R.string.trending_today);

    trendingRobot.withRepositoryItemAt(0).clickOnRow();
    trendingRobot.withRepositoryItemAt(1).clickOnRow();

    intended(allOf(hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/frappe/charts")));
    intended(allOf(hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/RedditSota/machine-learning-problems")));

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
}
