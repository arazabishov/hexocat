package com.abishov.hexocat.buildsrc

object AndroidSdk {
    object Version {
        const val min = 24
        const val compile = 30
    }
}

object Kotlin {
    const val version = "1.5.21"
    const val coroutines = "1.5.2"
}

object Jetpack {

    object Compose {
        const val version = "1.0.2"
        const val ui = "androidx.compose.ui:ui:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val foundation = "androidx.compose.foundation:foundation:$version"
        const val material = "androidx.compose.material:material:$version"
        const val icons = "androidx.compose.material:material-icons-extended:$version"

        object Test {
            const val uiTest = "androidx.compose.ui:ui-test:$version"
            const val uiTestJUnit = "androidx.compose.ui:ui-test-junit4:$version"
        }
    }

    // TODO: replace with Compose.material
    const val material = "com.google.android.material:material:1.4.0"
}

object AndroidX {

    object Lifecycle {
        const val version = "2.3.1"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
    }

    object Test {
        const val androidJUnit = "androidx.test.ext:junit:1.1.3"
        const val orchestrator = "androidx.test:orchestrator:1.4.0"
        const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val rules = "androidx.test:rules:1.4.0"
        
        object Espresso {
            const val version = "3.3.0"
            const val core = "androidx.test.espresso:espresso-core:$version"
            const val intents = "androidx.test.espresso:espresso-intents:$version"
            const val contrib = "androidx.test.espresso:espresso-contrib:$version"
            const val idlingResource = "androidx.test.espresso:espresso-idling-resource:$version"
        }
    }

    const val annotation = "androidx.annotation:annotation:1.2.0"
    const val appcompat = "androidx.appcompat:appcompat:1.3.1"
}

object Libraries {
    const val flowlayout = "com.google.accompanist:accompanist-flowlayout:0.18.0"
    const val coil = "io.coil-kt:coil-compose:1.3.2"

    const val kotlin = Kotlin.version
    const val apollo = "2.5.9"
    const val coroutines = Kotlin.coroutines
    const val dagger = "2.38.1"
    const val okhttp = "4.9.1"
    const val leakcanary = "1.5.4"
    const val threetenabp = "1.2.4"
    const val timber = "5.0.1"
    const val paperwork = "1.2.7"
    const val junit = "4.13.1"
    const val assertj = "2.8.0"
    const val restmock = "0.4.3"
    const val mockito = "3.7.7"
    const val mockitok = "2.2.0"
    const val assertk = "0.23"
    const val okhttpIdlingResource = "1.0.0"
    const val roboelectric = "4.5"
    const val androidGradlePlugin = "7.0.2"
    const val buildKonfig = "0.7.0"
    const val gradleVersions = "0.36.0"
}
