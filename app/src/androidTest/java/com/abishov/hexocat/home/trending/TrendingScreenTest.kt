package com.abishov.hexocat.home.trending

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.abishov.hexocat.common.rule.MockWebServerRule
import com.abishov.hexocat.home.HomeActivity
import io.appflate.restmock.RESTMockServer.whenGET
import io.appflate.restmock.RequestsVerifier.verifyGET
import io.appflate.restmock.utils.QueryParam
import io.appflate.restmock.utils.RequestMatchers.hasExactQueryParameters
import io.appflate.restmock.utils.RequestMatchers.pathContains
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class TrendingScreenTest {

  @get:Rule
  val activityTestRule = IntentsTestRule(
    HomeActivity::class.java, false, false
  )

  @get:Rule
  val mockWebServerRule = MockWebServerRule()

  @Test
  fun mustRenderTrendingRepositoriesForToday() {
    whenGET(
      allOf<RecordedRequest>(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnFile("response/search/repositories/200_trending_today.json")

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      repositoryAt(0) {
        name("charts")
        stars(8760)
        forks(263)
        description(
          "frappe — Responsive, modern " +
              "SVG Charts with zero dependencies"
        )
      }

      repositoryAt(1) {
        name("state-of-the-art-result-for-machine-learning-problems")
        stars(4238)
        forks(597)
        description(
          "RedditSota — This repository provides state of the art " +
              "(SoTA) results for all machine learning problems."
        )
      }
    }

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).never()
  }

  @Test
  fun mustRenderTrendingRepositoriesForWeek() {
    whenGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnFile("response/search/repositories/200_trending_week.json")

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      swipeLeft()

      repositoryAt(0) {
        name("hangzhouYunQi2017ppt")
        stars(3998)
        forks(1649)
        description("Alibaba-Technology")
      }

      repositoryAt(1) {
        name("bottery")
        stars(3131)
        forks(143)
        description("google")
      }
    }

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)
  }

  @Test
  fun mustRenderTrendingRepositoriesForMonth() {
    whenGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnFile("response/search/repositories/200_trending_month.json")

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      swipeLeft()
      swipeLeft()

      repositoryAt(0) {
        name("deepo")
        stars(2699)
        forks(163)
        description(
          "ufoym — A Docker image containing almost all popular" +
              " deep learning frameworks."
        )
      }

      repositoryAt(1) {
        name("nba-go")
        stars(2575)
        forks(118)
        description("xxhomey19 — The finest NBA CLI.")
      }
    }

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)
  }

  @Test
  fun mustRender400ErrorMessage() {
    whenGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnEmpty(400)

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      errorMessage("HTTP 400 ")
      retryButtonIsVisible()
    }

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).never()
  }

  @Test
  fun mustRender500ErrorMessage() {
    whenGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnEmpty(500)

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      errorMessage("HTTP 500 ")
      retryButtonIsVisible()
    }

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).never()
  }

  @Test
  fun mustRequestBackendAfterRetryButtonClicked() {
    whenGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnEmpty(400)

    activityTestRule.launchActivity(Intent())

    trendingScreen {
      errorMessage("HTTP 400 ")
      retryButtonIsVisible()
      retry()
    }

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(2)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).never()
  }

  @Test
  fun mustNavigateToBrowserOnRepositoryItemClicked() {
    whenGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).thenReturnFile("response/search/repositories/200_trending_today.json")

    activityTestRule.launchActivity(Intent())

    intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))

    trendingScreen {
      repositoryAt(0) {
        clickOnRow()
      }

      repositoryAt(1) {
        clickOnRow()
      }
    }

    intended(
      allOf(
        hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/frappe/charts")
      )
    )
    intended(
      allOf(
        hasAction(Intent.ACTION_VIEW),
        hasData("https://github.com/RedditSota/machine-learning-problems")
      )
    )

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-29"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-08-23"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).exactly(1)

    verifyGET(
      allOf(
        pathContains("search/repositories"),
        hasExactQueryParameters(
          QueryParam("q", "created:>=2017-07-31"),
          QueryParam("sort", "watchers"),
          QueryParam("order", "desc")
        )
      )
    ).never()
  }
}
