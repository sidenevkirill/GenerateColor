plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ru.lisdevs.generaterandom"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.lisdevs.generaterandom"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "1.3"

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
}

dependencies {
    implementation ("androidx.palette:palette:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("androidx.preference:preference:1.1.1")
    implementation(libs.recyclerview)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}