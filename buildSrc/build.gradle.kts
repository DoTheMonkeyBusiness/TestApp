plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
}