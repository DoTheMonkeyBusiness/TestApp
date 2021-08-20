import extension.getString

plugins {
    id("com.android.application")
    id("com.squareup.sqldelight")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("android")
    kotlin("plugin.serialization") version GradleConstants.Versions.serialization
}

val appVersionCode = 1
val appVersionName = "1.0"

android {
    compileSdk = GradleConstants.Versions.compileSDK

    defaultConfig {
        applicationId = GradleConstants.appId
        minSdk = GradleConstants.Versions.minSDK
        targetSdk = GradleConstants.Versions.targetSDK
        versionCode = appVersionCode
        versionName = appVersionName
    }

    signingConfigs {
        create("release") {
            val properties = org.jetbrains.kotlin.konan.properties.loadProperties("$rootDir/local.properties")

            storeFile = file("$rootDir/release-keystore")
            storePassword = properties.getString("storePassword")
            keyAlias = properties.getString("keyAlias")
            keyPassword = properties.getString("keyPassword")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        unitTests.apply {
            isReturnDefaultValues = true
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}

dependencies {
    implementation(libs.accompanist.placeholder)

    implementation(libs.android.material)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.coil.kt)
    implementation(libs.glide.kt)

    implementation(libs.compose.ui.base)
    implementation(libs.compose.ui.foundation)
    implementation(libs.compose.ui.material)
    implementation(libs.compose.ui.preview)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.util)

    implementation(libs.koin.compose)

    implementation(libs.kotlin.coroutines.android)

    implementation(libs.kotlin.serialization.json)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)

    implementation(libs.logger.kermit)

    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)

    testImplementation(libs.test.junit.jupiter.main)
    testImplementation(libs.test.junit.jupiter.api)
    testImplementation(libs.test.junit.jupiter.params)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.sqldelight)

    testRuntimeOnly(libs.test.junit.jupiter.engine)
}
