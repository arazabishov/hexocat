package com.abishov.hexocat.android.home.trending

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import com.abishov.hexocat.android.HexocatTestApp
import com.abishov.hexocat.android.common.rule.CustomActivityRule
import com.abishov.hexocat.android.common.rule.MockWebServerRule
import com.abishov.hexocat.android.home.HomeActivity
import io.appflate.restmock.RESTMockServer.whenPOST
import io.appflate.restmock.RequestsVerifier.verifyPOST
import io.appflate.restmock.utils.RequestMatchers.pathContains
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

class TrendingScreenTest {
    private lateinit var context: Context
    private val composeTestRule = AndroidComposeTestRule(
        activityRule = CustomActivityRule(HomeActivity::class.java),
        activityProvider = { it.activity }
    )

    @get:Rule
    val chainedRule: RuleChain = RuleChain.outerRule(MockWebServerRule())
        .around(composeTestRule)

    private fun trendingScreen(func: TrendingRobot.() -> Unit) {
        trendingScreen(composeTestRule, context, func)
    }

    @Before
    fun setUp() {
        context = HexocatTestApp.instance
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun mustRenderTrendingRepositoriesForToday() {
        whenPOST(pathContains("graphql"))
            .thenReturnFile("response/search/repositories/200_trending.json")

        composeTestRule.activityRule.launch()

        trendingScreen {
            withRepository("pdd_3years") {
                owner("LeadroyaL")
                description("我在拼多多的三年，以及网站崩溃时候的日志文件")
                stars(1726)
            }
            withRepository("jd_scripts") {
                owner("LXK9301")
                primaryLanguage("JavaScript")
                stars(1703)
            }
            withRepository("best-of-python") {
                owner("ml-tooling")
                description("🏆 A ranked list of awesome Python open-source libraries and tools.")
                primaryLanguage("Python")
                topics("best-of", "awesome")
                stars(1297)
            }
        }

        verifyPOST(pathContains("graphql"))
    }

    @Test
    fun mustRender400ErrorMessage() {
        whenPOST(pathContains("graphql"))
            .thenReturnEmpty(400)

        composeTestRule.activityRule.launch()

        trendingScreen {
            errorMessage("400")
            retryButtonIsVisible()
        }

        verifyPOST(pathContains("graphql"))
    }

    @Test
    fun mustRender500ErrorMessage() {
        whenPOST(pathContains("graphql"))
            .thenReturnEmpty(500)

        composeTestRule.activityRule.launch()

        trendingScreen {
            errorMessage("500")
            retryButtonIsVisible()
        }

        verifyPOST(pathContains("graphql"))
    }

    @Test
    fun mustRequestBackendAfterRetryButtonClicked() {
        whenPOST(pathContains("graphql"))
            .thenReturnEmpty(400)

        composeTestRule.activityRule.launch()

        trendingScreen {
            errorMessage("400")
            retryButtonIsVisible()
            retry()

            // since mocked server returns 400, the
            // same result is expected
            errorMessage("400")
            retryButtonIsVisible()
        }

        verifyPOST(pathContains("graphql"))
            .exactly(2)
    }

    @Test
    fun mustNavigateToBrowserOnRepositoryItemClicked() {
        val verifyClickedOn: (String) -> Unit = { url ->
            intended(allOf(hasAction(Intent.ACTION_VIEW), hasData(url)))
        }

        whenPOST(pathContains("graphql"))
            .thenReturnFile("response/search/repositories/200_trending.json")

        composeTestRule.activityRule.launch()

        intending(not(isInternal())).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                null
            )
        )

        trendingScreen {
            withRepository("pdd_3years") { clickOnRow() }
            withRepository("jd_scripts") { clickOnRow() }
            withRepository("best-of-python") { clickOnRow() }
        }

        verifyClickedOn("https://github.com/LeadroyaL/pdd_3years")
        verifyClickedOn("https://github.com/LXK9301/jd_scripts")
        verifyClickedOn("https://github.com/ml-tooling/best-of-python")

        verifyPOST(pathContains("graphql"))
    }
}
