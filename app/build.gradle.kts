// Apply necessary plugins for Android, Kotlin, Dagger Hilt, and Kotlin Kapt
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

// Android configuration
android {
    namespace = "com.example.weatherassist"
    compileSdk = 34

    // Default configuration for the app
    defaultConfig {
        applicationId = "com.example.weatherassist"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Build types configuration
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Java and Kotlin compatibility options
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Enable Compose for this project
    buildFeatures {
        compose = true
    }

    // Compose configuration
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    // Packaging configuration to exclude specific resources
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Project dependencies
dependencies {
    implementation("com.google.android.gms:play-services-location:21.0.1")
    //for milestone 3
    val roomVersion = "2.6.0"

    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // AndroidX and Kotlin dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material:1.5.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Dagger Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Coroutines dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Coroutine Lifecycle Scopes dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Coil dependency for image loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Retrofit and OkHttp dependencies for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    // JSON Converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    implementation("androidx.room:room-ktx:$roomVersion")

    // Preferences Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation ("androidx.compose.runtime:runtime:1.5.4")


}