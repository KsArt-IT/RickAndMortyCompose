buildscript {
    val hiltVersion by extra("2.44.2")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("com.android.tools.build:gradle:7.4.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.android").version("1.7.20") apply false
}

apply(from = "$rootDir/ktlint.gradle.kts")
