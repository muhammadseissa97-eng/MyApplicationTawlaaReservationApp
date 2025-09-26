buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // أضف هذا
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath("com.google.gms:google-services:4.4.0")

        // أضف هذه إذا كنت تستخدم Firebase BoM
        classpath("com.google.firebase:firebase-appdistribution-gradle:4.0.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() { // لأجل التوافقية
            content {
                includeModule("com.google.gms", "google-services")
            }
        }
    }
}

