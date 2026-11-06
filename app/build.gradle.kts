import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

val apiUrl : String = gradleLocalProperties(rootDir, providers).getProperty("apiUrl")
val pathUrl : String = gradleLocalProperties(rootDir, providers).getProperty("pathUrl")

android {
    namespace = "com.bayutb123.tukerin"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.tukerin.app"
        minSdk = 25
        targetSdk = 34
        versionCode = 4
        versionName = "1.3.0-alpha"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "apiUrl", "\"$apiUrl\"")
            buildConfigField("String", "pathUrl", "\"$pathUrl\"")
        }
        release {
            buildConfigField("String", "apiUrl", "\"$apiUrl\"")
            buildConfigField("String", "pathUrl", "\"$pathUrl\"")
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            // resources.excludes.add("META-INF/*")
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation("androidx.core:core-ktx:1.19.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Navigation
    val navVersion = "2.10.2"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended")

    // Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // hiltViewModel() for Compose (moved here from hilt-navigation-compose)
    implementation("androidx.hilt:hilt-lifecycle-viewmodel-compose:1.4.0")

    // Lifecycle
    val lifecycleVersion = "2.11.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:6.7.1")

    // ROOM
    val roomVersion = "2.8.5"
    implementation("androidx.room:room-common:$roomVersion")
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")

    // Zoomable Image
    implementation("net.engawapg.lib:zoomable:2.16.0")
}
