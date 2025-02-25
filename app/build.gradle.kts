import org.gradle.internal.impldep.org.bouncycastle.bcpg.PacketTags.SECRET_KEY

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.aitarotreadingapp"
    compileSdk = 35

    defaultConfig {
        buildConfigField (type = "String",
            name = "SECRET_API_KEY",
            value = "${project.property("SECRET_API_KEY")}")
        applicationId = "com.example.aitarotreadingapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    dataBinding{
        enable = true
    }
    android.buildFeatures.buildConfig = true
}

dependencies {

    //Room
    val room_version = "2.6.1"
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.3")

    //RetroFit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //-->
    val lifecycle_version = "2.8.6"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    //<--

    //Gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}