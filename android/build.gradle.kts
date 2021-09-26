import com.abishov.hexocat.buildsrc.AndroidSdk
import com.abishov.hexocat.buildsrc.Libraries
import com.abishov.hexocat.buildsrc.AndroidX
import com.abishov.hexocat.buildsrc.Jetpack

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

kotlin {
    sourceSets.all {
        languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
    }
}

android {
    compileSdk = AndroidSdk.Version.compile

    defaultConfig {
        targetSdk = AndroidSdk.Version.compile
        minSdk = AndroidSdk.Version.min

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
        kotlinCompilerExtensionVersion = Jetpack.Compose.version
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    testCoverage {
        jacocoVersion = "0.8.7"
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
        resources {
            excludes.addAll(
                setOf(
                    "**/attach_hotspot_windows.dll",
                    "META-INF/AL2.0",
                    "META-INF/LGPL2.1",
                    "META-INF/LICENSE",
                    "META-INF/licenses/ASM",
                )
            )
        }
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

fun DependencyHandlerScope.androidx() {
    implementation(AndroidX.Lifecycle.viewmodel)
    implementation(AndroidX.Lifecycle.livedata)

    implementation(AndroidX.annotation)
    implementation(AndroidX.appcompat)

    testImplementation(AndroidX.Test.androidJUnit)
    testImplementation(AndroidX.Test.archCoreTesting)

    androidTestUtil(AndroidX.Test.orchestrator)
    androidTestImplementation(AndroidX.Test.rules)
    androidTestImplementation(AndroidX.Test.Espresso.core)
    androidTestImplementation(AndroidX.Test.Espresso.intents)
    androidTestImplementation(AndroidX.Test.Espresso.contrib)
    androidTestImplementation(AndroidX.Test.Espresso.idlingResource)
}

fun DependencyHandlerScope.jetpack() {
    implementation(Jetpack.material)
    implementation(Jetpack.Compose.ui)
    implementation(Jetpack.Compose.uiTooling)
    implementation(Jetpack.Compose.foundation)
    implementation(Jetpack.Compose.material)
    implementation(Jetpack.Compose.icons)

    androidTestImplementation(Jetpack.Compose.Test.uiTest)
    androidTestImplementation(Jetpack.Compose.Test.uiTestJUnit)
}

dependencies {
    implementation(project(":shared"))

    androidx()
    jetpack()

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Libraries.kotlin}")
    implementation("hu.supercluster:paperwork:${Libraries.paperwork}")

    implementation(Libraries.coil)
    implementation(Libraries.flowlayout)

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
    testImplementation("com.apollographql.apollo:apollo-api:${Libraries.apollo}")

    androidTestImplementation("com.github.andrzejchm.RESTMock:android:${Libraries.restmock}")
    androidTestImplementation("com.squareup.okhttp3:mockwebserver:${Libraries.okhttp}")
    androidTestImplementation("org.mockito:mockito-android:${Libraries.mockito}")
    androidTestImplementation("com.jakewharton.espresso:okhttp3-idling-resource:${Libraries.okhttpIdlingResource}")
}
