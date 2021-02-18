package com.abishov.hexocat.android.common.rule

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.core.app.ActivityScenario
import org.junit.rules.ExternalResource

inline fun <reified A : ComponentActivity> createDelayedActivityComposeRule():
        AndroidComposeTestRule<DelayedActivityLaunchRule<A>, A> {
    return AndroidComposeTestRule(
        activityRule = DelayedActivityLaunchRule(A::class.java),
        activityProvider = { it.activity }
    )
}

fun <A : ComponentActivity> AndroidComposeTestRule<DelayedActivityLaunchRule<A>, A>.launch() {
    activityRule.launch()
}

class DelayedActivityLaunchRule<A : Activity>(activity: Class<A>) : ExternalResource() {
    private val scenarioProvider: () -> ActivityScenario<A> = {
        ActivityScenario.launch(activity)
    }

    private lateinit var scenario: ActivityScenario<A>
    lateinit var activity: A

    fun launch() {
        scenario = scenarioProvider()
            .onActivity {
                activity = it
            }
    }

    override fun after() {
        scenario.close()
    }
}
