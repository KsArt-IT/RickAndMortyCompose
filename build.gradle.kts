buildscript {
    val hiltVersion by extra("2.45")

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("com.android.tools.build:gradle:8.1.0-alpha07")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

apply(from = "$rootDir/ktlint.gradle.kts")
