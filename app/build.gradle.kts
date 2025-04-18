plugins {
//    id("com.android.application")
//    id("org.jetbrains.kotlin.android") // required even for Java+Realm
//    id("io.realm.kotlin") version "1.11.0"
  alias(libs.plugins.android.application)

}

android {
    namespace = "com.example.hrvhealthtracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.hrvhealthtracker"
        minSdk = 27
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.core.ktx)
    implementation(libs.play.services.wearable.v1810)




}


