plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
    id("dagger.hilt.android.plugin")
}

val kotlinVersion = rootProject.extra["kotlinVersion"] as String
val roomVersion = rootProject.extra["roomVersion"] as String
val hiltVersion = rootProject.extra["hiltVersion"] as String

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.primarydetail"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
        }
    }

    // Need to include this for lambda
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
    namespace = "com.example.primarydetail"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    // AndroidX Core
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Fragments/Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    @Suppress("LifecycleAnnotationProcessorWithJava8")
    kapt("androidx.lifecycle:lifecycle-compiler:2.4.1")

    // Room Database
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // Material
    implementation("com.google.android.material:material:1.7.0-alpha02")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Moshi
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Retrofit/okhttp logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")

    // Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    // Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}