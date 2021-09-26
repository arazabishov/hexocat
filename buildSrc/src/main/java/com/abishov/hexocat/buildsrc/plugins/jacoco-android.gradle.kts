package com.abishov.hexocat.buildsrc.plugins

plugins {
    id("jacoco")
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

val debugCoverageSourceDirs = files(listOf("src/main/java", "src/debug/java"))

val debugExecutionData = fileTree(baseDir = "$buildDir") {
    include("jacoco/testDebugUnitTest.exec", "outputs/code_coverage/debugAndroidTest/connected/**/*.ec")
}

val debugClassDirectories = fileTree(baseDir = "${project.buildDir}/intermediates/classes/debug") {
    exclude(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*\$Lambda$*.*",
        "**/*\$inlined$*.*",
        "**/*Module.*",
        "**/*Dagger*.*",
        "**/*MembersInjector*.*",
        "**/*_Provide*Factory*.*"
    )
}

fun JacocoReportsContainer.reports() {
    xml.isEnabled = true
    html.isEnabled = true
}

fun JacocoReport.setDirectories() {
    additionalSourceDirs.setFrom(debugCoverageSourceDirs)
    sourceDirectories.setFrom(debugCoverageSourceDirs)
    classDirectories.setFrom(debugClassDirectories)
    executionData.setFrom(debugExecutionData)
}

fun JacocoCoverageVerification.setDirectories() {
    additionalSourceDirs.setFrom(debugCoverageSourceDirs)
    sourceDirectories.setFrom(debugCoverageSourceDirs)
    classDirectories.setFrom(debugClassDirectories)
    executionData.setFrom(debugExecutionData)
}

if (tasks.findByName("jacocoAndroidTestReport") == null) {
    tasks.register<JacocoReport>("jacocoAndroidTestReport") {
        group = "Reporting"
        description = "Code coverage report for both Android and Unit tests."
        dependsOn("testDebugUnitTest", "createDebugCoverageReport")
        reports {
            reports()
        }
        setDirectories()
    }
}

if (tasks.findByName("jacocoAndroidCoverageVerification") == null) {
    tasks.register<JacocoCoverageVerification>("jacocoAndroidCoverageVerification") {
        group = "Reporting"
        description = "Code coverage verification for Android both Android and Unit tests."
        dependsOn("testDebugUnitTest", "createDebugCoverageReport")
        violationRules {
            rule {
                limit {
                    minimum = "0.6".toBigDecimal()
                }
            }
        }
        setDirectories()
    }
}
