buildscript {
    val hiltVersion by extra("2.45")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("com.android.tools.build:gradle:7.4.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.android").version("1.8.10") apply false
}

apply(from = "$rootDir/ktlint.gradle.kts")
