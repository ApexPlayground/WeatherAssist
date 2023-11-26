// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    // Apply the Kotlin Android plugin with version 1.9.10
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    // Apply the Android library plugin with version 8.0.2,
    id("com.android.library") version "8.0.2" apply false
    // Apply the Dagger Hilt Android plugin with version 2.44,
    id ("com.google.dagger.hilt.android") version "2.44" apply false

}