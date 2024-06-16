plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.parcelize")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.primarydetail"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    // Need to include this for lambda
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        allWarningsAsErrors = true
    }

    namespace = "com.example.primarydetail"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.kotlin.stdlib)

    // AndroidX Core
    implementation(libs.preference)
    implementation(libs.appcompat)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Material
    implementation(libs.material)

    // Kotlin Coroutines
    runtimeOnly(libs.coroutines.android)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Kotlin Serialziation
    implementation(libs.kotlin.serialization.json)

    // Hilt
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    // Compose
    implementation(libs.bundles.compose)

    // Compose M3 Adaptive
    implementation(libs.bundles.compose.material3.adaptive)

    // Accompanist
    implementation(libs.bundles.accompanist)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.expresso.core)
}
