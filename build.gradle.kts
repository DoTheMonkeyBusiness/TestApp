buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    // fixme
    // workaround for no support of "libs" in buildscript (https://github.com/gradle/gradle/issues/16958#issuecomment-827140071)
    // (https://discuss.gradle.org/t/trouble-using-centralized-dependency-versions-in-buildsrc-plugins-and-buildscript-classpath/39421)
    val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs") as org.gradle.accessors.dm.LibrariesForLibs

    dependencies {
        classpath(libs.plugin.kotlin.gradle)
        classpath(libs.plugin.android.gradle)
        classpath(libs.plugin.secrets.gradle)
        classpath(libs.plugin.sqldelight.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
