// Top-level build file where you can add configuration options common to all sub-projects/modules.
val kotlinVersion by extra("1.7.0")
val roomVersion by extra("2.4.2")
val hiltVersion by extra("2.42")
val composeVersion by extra("1.3.0-alpha01")
val accompanistVersion by extra("0.24.12-rc")

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}
