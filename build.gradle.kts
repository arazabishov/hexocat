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

    dependencies {
        classpath("com.apollographql.apollo:apollo-gradle-plugin:$apollo")
        classpath("com.android.tools.build:gradle:4.2.0-beta04")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.36.0")
        classpath("hu.supercluster:paperwork-plugin:$paperwork")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
