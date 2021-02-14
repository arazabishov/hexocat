buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    // TODO: replace FQCN once this bug is fixed: https://github.com/gradle/gradle/issues/9270
    //  kotlin-dsl plugin for gradle fails to resolve classes imported from buildSrc
    val kotlin = com.abishov.hexocat.buildsrc.Libraries.kotlin
    val apollo = com.abishov.hexocat.buildsrc.Libraries.apollo
    val paperwork = com.abishov.hexocat.buildsrc.Libraries.paperwork
    val androidGradlePlugin = com.abishov.hexocat.buildsrc.Libraries.androidGradlePlugin
    val buildKonfig = com.abishov.hexocat.buildsrc.Libraries.buildKonfig
    val gradleVersions = com.abishov.hexocat.buildsrc.Libraries.gradleVersions

    dependencies {
        classpath("com.apollographql.apollo:apollo-gradle-plugin:$apollo")
        classpath("com.android.tools.build:gradle:$androidGradlePlugin")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
        classpath("com.github.ben-manes:gradle-versions-plugin:$gradleVersions")
        classpath("hu.supercluster:paperwork-plugin:$paperwork")
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:$buildKonfig")
    }
}

allprojects {
    val jitpackUri = java.net.URI.create("https://jitpack.io")

    repositories {
        google()
        jcenter()
        mavenCentral()
        maven {
            url = jitpackUri
        }
    }
}
