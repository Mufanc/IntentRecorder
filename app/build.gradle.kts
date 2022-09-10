plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

val androidCompileSdkVersion: Int by rootProject.extra
val androidMinSdkVersion: Int by rootProject.extra
val androidTargetSdkVersion: Int by rootProject.extra
val androidSourceCompatibility: JavaVersion by rootProject.extra
val androidTargetCompatibility: JavaVersion by rootProject.extra
val kotlinJvmTarget: String by rootProject.extra

android {
    compileSdk = androidCompileSdkVersion

    defaultConfig {
        applicationId = "xyz.mufanc.macro"
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = androidSourceCompatibility
        targetCompatibility = androidTargetCompatibility
    }

    kotlinOptions {
        jvmTarget = kotlinJvmTarget
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")

    ksp(project(":easyhook:ksp-xposed"))
    implementation(project(":easyhook:api"))
    compileOnly("de.robv.android.xposed:api:82")
}
