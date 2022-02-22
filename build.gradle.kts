// Top-level build file where you can add configuration options common to all sub-projects/modules.
val kotlinVersion by extra("1.6.10")
val roomVersion by extra("2.4.1")
val hiltVersion by extra("2.41")
val composeVersion by extra("1.2.0-alpha03")
val accompanistVersion by extra("0.24.2-alpha")

plugins {
    id("com.android.application") version "7.1.1" apply false
    id("com.android.library") version "7.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
    }
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}