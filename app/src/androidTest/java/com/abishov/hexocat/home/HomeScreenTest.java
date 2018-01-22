package com.abishov.hexocat.home;

import static io.appflate.restmock.RESTMockServer.whenGET;
import static io.appflate.restmock.utils.RequestMatchers.hasExactQueryParameters;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.hamcrest.CoreMatchers.allOf;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import com.abishov.hexocat.HexocatTestApp;
import com.abishov.hexocat.common.rule.CaptureScreenshots;
import com.abishov.hexocat.common.rule.CaptureScreenshotsRule;
import com.abishov.hexocat.home.trending.TrendingRobot;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;
import io.appflate.restmock.utils.QueryParam;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
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
    HexocatTestApp.overrideBaseUrl(HttpUrl.parse(RESTMockServer.getUrl()));
    RESTMockServer.reset();

    OkHttpClient client = HexocatTestApp.getInstance().appComponent().okHttpClient();
    Espresso.registerIdlingResources(OkHttp3IdlingResource.create("okhttp", client));

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
    trendingRobot.withRepositoryItemAt(2)
        .withName("hangzhouYunQi2017ppt")
        .withStars(3998)
        .withForks(1649)
        .withDescription("Alibaba-Technology");
    trendingRobot.withRepositoryItemAt(3)
        .withName("bottery")
        .withStars(3131)
        .withForks(143)
        .withDescription("google");
    trendingRobot.withRepositoryItemAt(4)
        .withName("deepo")
        .withStars(2699)
        .withForks(163)
        .withDescription("ufoym — A Docker image containing almost all popular"
            + " deep learning frameworks.");
    trendingRobot.withRepositoryItemAt(5)
        .withName("nba-go")
        .withStars(2575)
        .withForks(118)
        .withDescription("xxhomey19 — The finest NBA CLI.");

    RequestsVerifier.verifyGET(allOf(pathContains("search/repositories"), hasExactQueryParameters(
        new QueryParam("q", "created:>=2017-08-29"),
        new QueryParam("sort", "watchers"),
        new QueryParam("order", "desc"))
    )).exactly(1);

    RequestsVerifier.verifyGET(allOf(pathContains("search/repositories"), hasExactQueryParameters(
        new QueryParam("q", "created:>=2017-08-23"),
        new QueryParam("sort", "watchers"),
        new QueryParam("order", "desc"))
    )).exactly(1);
  }
}
