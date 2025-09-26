pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.android.application") version "8.3.0" apply false
        id("org.jetbrains.kotlin.android") version "1.9.22" apply false
        id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false
        id("com.google.gms.google-services") version "4.3.15" apply false
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplicationTawlaaReservationApp"
include(":app")
