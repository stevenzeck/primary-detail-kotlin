import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.agp)
    alias(libs.plugins.kotlin)
    kotlin("plugin.parcelize")
    kotlin("plugin.serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.primarydetail"
        minSdk = 21
        targetSdk = 36
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
        buildConfig = true
    }

    composeCompiler {

    }

    namespace = "com.example.primarydetail"
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        allWarningsAsErrors = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.kotlin.stdlib)

    // AndroidX Core
    implementation(libs.androidx.preference)
    implementation(libs.androidx.appcompat)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel)

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

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

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ext.junit)
    androidTestImplementation(libs.androidx.expresso.core)
}
