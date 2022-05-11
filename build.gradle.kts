// Top-level build file where you can add configuration options common to all sub-projects/modules.
val kotlinVersion by extra("1.6.21")
val roomVersion by extra("2.4.2")
val hiltVersion by extra("2.41")
val composeVersion by extra("1.2.0-alpha08")
val accompanistVersion by extra("0.24.7-alpha")

plugins {
    id("com.android.application") version "7.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
    id("com.google.dagger.hilt.android") version "2.41" apply false
}

tasks.register("clean", Delete::class).configure {
    delete(rootProject.buildDir)
}