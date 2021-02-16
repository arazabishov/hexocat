import com.abishov.hexocat.buildsrc.AndroidSdk
import com.abishov.hexocat.buildsrc.Libraries

plugins {
    id("com.android.application")
    id("hu.supercluster.paperwork")
    id("com.abishov.hexocat.buildsrc.plugins.jacoco-android")

    kotlin("android")
    kotlin("kapt")
}

object Version {
    private const val versionMajor = 0
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionBuild = 1

    val code: Int
        get() = versionMajor * 10000 + versionMinor * 1000 + versionPatch * 100 + versionBuild
    val name: String
        get() = "${versionMajor}.${versionMinor}.${versionPatch}"
}

android {
    compileSdkVersion(AndroidSdk.Version.compile)

    defaultConfig {
        targetSdkVersion(AndroidSdk.Version.compile)
        minSdkVersion(AndroidSdk.Version.min)

        applicationId = "com.abishov.hexocat.android"
        versionCode = Version.code
        versionName = Version.name

        testInstrumentationRunner = "com.abishov.hexocat.android.HexocatTestRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = Libraries.kotlin
        kotlinCompilerExtensionVersion = Libraries.compose
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    packagingOptions {
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/LICENSE")
        exclude("META-INF/licenses/ASM")
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true

        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

paperwork {
    set = mapOf(
        "gitSha" to gitSha(),
        "buildDate" to buildTime("yyyy-MM-dd HH:mm:ss")
    )
}

dependencies {
    implementation(project(":shared"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Libraries.kotlin}")
    implementation("hu.supercluster:paperwork:${Libraries.paperwork}")

    implementation("com.google.android.material:material:${Libraries.material}")
    implementation("androidx.appcompat:appcompat:${Libraries.appcompat}")
    implementation("androidx.annotation:annotation:${Libraries.annotation}")

    implementation("androidx.compose.ui:ui:${Libraries.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Libraries.compose}")
    implementation("androidx.compose.foundation:foundation:${Libraries.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Libraries.compose}")
    implementation("androidx.compose.material:material:${Libraries.compose}")
    implementation("dev.chrisbanes.accompanist:accompanist-coil:${Libraries.accompanist}")

    implementation("androidx.lifecycle:lifecycle-extensions:${Libraries.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Libraries.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Libraries.lifecycle}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Libraries.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Libraries.coroutines}")

    kapt("com.google.dagger:dagger-compiler:${Libraries.dagger}")
    implementation("com.google.dagger:dagger:${Libraries.dagger}")

    kapt("com.google.dagger:dagger-android-processor:${Libraries.dagger}")
    implementation("com.google.dagger:dagger-android-support:${Libraries.dagger}")

    implementation("com.squareup.okhttp3:okhttp:${Libraries.okhttp}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Libraries.okhttp}")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:${Libraries.leakcanary}")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-no-op:${Libraries.leakcanary}")

    implementation("com.jakewharton.timber:timber:${Libraries.timber}")
    implementation("com.jakewharton.threetenabp:threetenabp:${Libraries.threetenabp}")

    testImplementation("junit:junit:${Libraries.junit}")
    testImplementation("org.assertj:assertj-core:${Libraries.assertj}")
    testImplementation("org.mockito:mockito-core:${Libraries.mockito}")
    testImplementation("com.squareup.leakcanary:leakcanary-android-no-op:${Libraries.leakcanary}")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libraries.coroutines}")
    testImplementation("com.willowtreeapps.assertk:assertk:${Libraries.assertk}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${Libraries.mockitok}")
    testImplementation("org.robolectric:robolectric:${Libraries.roboelectric}")
    testImplementation("androidx.test.ext:junit:${Libraries.androidJunit}")
    testImplementation("android.arch.core:core-testing:${Libraries.archTesting}")
    testImplementation("com.apollographql.apollo:apollo-api:${Libraries.apollo}")

    androidTestUtil("androidx.test:orchestrator:${Libraries.orchestrator}")
    androidTestImplementation("androidx.test:rules:${Libraries.testRules}")

    androidTestImplementation("androidx.compose.ui:ui-test:${Libraries.compose}")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:${Libraries.espresso}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Libraries.espresso}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${Libraries.espresso}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Libraries.espresso}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${Libraries.compose}")

    androidTestImplementation("com.github.andrzejchm.RESTMock:android:${Libraries.restmock}")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${Libraries.okhttp}")
    androidTestImplementation("org.mockito:mockito-android:${Libraries.mockito}")
    androidTestImplementation("com.jakewharton.espresso:okhttp3-idling-resource:${Libraries.okhttpIdlingResource}")
}
