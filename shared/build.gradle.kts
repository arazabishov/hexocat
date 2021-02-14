import com.abishov.hexocat.buildsrc.AndroidSdk
import com.abishov.hexocat.buildsrc.Libraries
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    id("com.android.library")
    id("com.apollographql.apollo")
    id("com.codingfeline.buildkonfig")

    kotlin("multiplatform")
}

kotlin {
    android()

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.apollographql.apollo:apollo-runtime-kotlin:${Libraries.apollo}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Libraries.coroutines}-native-mt") {
                    version {
                        strictly(Libraries.coroutines)
                    }
                }
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:${Libraries.junit}")
            }
        }

        val iosMain by getting
        val iosTest by getting
    }
}

android {
    sourceSets["main"].manifest
        .srcFile("src/androidMain/AndroidManifest.xml")

    compileSdkVersion(AndroidSdk.Version.compile)

    defaultConfig {
        targetSdkVersion(AndroidSdk.Version.compile)
        minSdkVersion(AndroidSdk.Version.min)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apollo {
    generateKotlinModels.set(true)
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

buildkonfig {
    packageName = "com.abishov.hexocat.shared"

    defaultConfigs {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "GITHUB_PAT",
            value = getGithubPat()
        )
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName)
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)
