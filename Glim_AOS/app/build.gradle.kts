import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.jeongbj.glim"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.jeongbj.glim"
        minSdk = 26
        targetSdk = 37
        versionCode = 11
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val keystoreProperties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            optimization {
                enable = true
            }
            signingConfig = signingConfigs.getByName("release")
        }
        create("releaseDebug") {
            initWith(getByName("release"))
            isDebuggable = true
            signingConfig = signingConfigs.getByName("release")
//            applicationIdSuffix = ".releasedebug"
//            versionNameSuffix = "-releaseDebug"

            matchingFallbacks += listOf("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    val kakaoNativeAppKey = (System.getenv("KAKAO_NATIVE_APP_KEY_GLIM") ?: "DEFAULT").lowercase()
    defaultConfig {
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY_GLIM", "\"$kakaoNativeAppKey\"")
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":core-android"))
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.material.icons)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    implementation(libs.timber)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.kakao.login)
    implementation(libs.firebase.messaging)
}