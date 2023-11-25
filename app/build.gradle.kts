@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
}

android {
    namespace = libs.versions.android.applicationId.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.android.applicationId.get()
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.compileSdk.get().toInt()
        versionCode = libs.versions.android.versionCode.get().toInt()
        versionName = libs.versions.android.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Lifecycle KTX
    implementation(libs.bundles.androidx.lifecycle)

    // Compose
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)

    implementation(libs.compose.navigation)

    implementation(libs.compose.compiler)
    implementation(libs.compose.animation)

    implementation(libs.compose.paging)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.compose.lifecycle.viewmodel)
    implementation(libs.compose.lifecycle.runtime)

    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material3)

    // Coroutines and Flow
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose.navigation)
    ksp(libs.hilt.android.compiler)

    // Coil
    implementation(libs.compose.coil)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.json)
    implementation(libs.kotlin.serialization.json)

    // Logging interceptor
    implementation(libs.okhttp.interceptor)

    // Date Time
    implementation(libs.kotlinx.datetime)

    // SplashScreen on devices prior Android 12
    implementation(libs.androidx.core.splashscreen)

    // WorkManager
    implementation(libs.work.runtime.ktx)
    implementation(libs.work.hilt)
    ksp(libs.hilt.compiler)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.compose.ui.test.junit4)

    debugImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.bundles.compose.ui.test)
}
