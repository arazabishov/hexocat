package com.abishov.hexocat.android.common.rule

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import org.junit.rules.ExternalResource

class CustomActivityRule<A : Activity>(activity: Class<A>) : ExternalResource() {
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
