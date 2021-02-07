import com.abishov.hexocat.buildsrc.AndroidSdk
import com.abishov.hexocat.buildsrc.Libraries
import java.util.*

plugins {
    id("com.android.application")
    id("com.apollographql.apollo")
    id("hu.supercluster.paperwork")

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

fun Project.properties(path: String): Properties? {
    val properties = Properties()
    val localProperties = rootProject.file(path)

    if (localProperties.exists()) {
        return properties.also { it.load(localProperties.inputStream()) }
    }

    return null
}

fun getGithubPat(): String {
    val localProperties = rootProject.properties("local.properties")

    var githubPat = localProperties?.getProperty("githubPat") ?: ""
    if (githubPat.isNotBlank()) {
        return githubPat
    }

    githubPat = System.getenv("GITHUB_PAT")
    if (githubPat.isNotBlank()) {
        return githubPat
    }

    throw InvalidUserDataException(
        "Neither 'githubPat' property " +
                "in the 'local.properties' file or 'GITHUB_PAT' environment variable was set."
    )
}

dependencies {
    implementation(project(":shared"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Libraries.kotlin}")
    implementation("hu.supercluster:paperwork:${Libraries.paperwork}")

    implementation("com.apollographql.apollo:apollo-runtime:${Libraries.apollo}")
    implementation("com.apollographql.apollo:apollo-coroutines-support:${Libraries.apollo}")

    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.annotation:annotation:1.1.0")

    implementation("androidx.compose.ui:ui:${Libraries.compose}")
    implementation("androidx.compose.ui:ui-tooling:${Libraries.compose}")
    implementation("androidx.compose.foundation:foundation:${Libraries.compose}")
    implementation("androidx.compose.material:material-icons-extended:${Libraries.compose}")
    implementation("androidx.compose.material:material:${Libraries.compose}")
    implementation("dev.chrisbanes.accompanist:accompanist-coil:0.4.2")

    implementation("androidx.lifecycle:lifecycle-extensions:${Libraries.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Libraries.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Libraries.lifecycle}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Libraries.coroutines}")
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
}

android {
    compileSdkVersion(AndroidSdk.Version.compile)

    defaultConfig {
        targetSdkVersion(AndroidSdk.Version.compile)
        minSdkVersion(AndroidSdk.Version.min)

        applicationId = "com.abishov.hexocat.android"
        versionCode = Version.code
        versionName = Version.name

        buildConfigField(type = "String", name = "GITHUB_PAT", value = "\"${getGithubPat()}\"")
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
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

apollo {
    generateKotlinModels.set(true)

    @Suppress("UnstableApiUsage")
    customTypeMapping.set(mapOf("URI" to "android.net.Uri"))
}

paperwork {
    set = mapOf(
        "gitSha" to gitSha(),
        "buildDate" to buildTime("yyyy-MM-dd HH:mm:ss")
    )
}
