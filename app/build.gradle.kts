plugins {
    id("com.android.application")
    kotlin("android")
}

val appVersionCode = 1
val appVersionName = "1.0"

android {
    compileSdk = Versions.compileSDK

    defaultConfig {
        applicationId = "com.nasalevich.testapp"
        minSdk = Versions.minSDK
        targetSdk = Versions.targetSDK
        versionCode = appVersionCode
        versionName = appVersionName
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {
    implementation(libs.accompanist.placeholder)

    implementation(libs.android.material)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.compose.ui.base)
    implementation(libs.compose.ui.foundation)
    implementation(libs.compose.ui.material)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.util)

    implementation(libs.kotlin.coroutines.android)
}