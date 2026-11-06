import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

val apiUrl : String = gradleLocalProperties(rootDir, providers).getProperty("apiUrl")
val pathUrl : String = gradleLocalProperties(rootDir, providers).getProperty("pathUrl")

android {
    namespace = "com.bayutb123.tukerin.core"
    compileSdk = 37

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "apiUrl", "\"$apiUrl\"")
        buildConfigField("String", "pathUrl", "\"$pathUrl\"")
    }

    buildTypes {
        debug {


        }
        release {
            // A library has no entry points, so shrinking it on its own (AGP 9)
            // strips the classes the app uses. The app's R8 shrinks the whole
            // program, including this module, and applies consumer-rules.pro.
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.19.1")
    implementation("androidx.appcompat:appcompat:1.8.0")
    implementation("com.google.android.material:material:1.14.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("com.google.android.gms:play-services-location:21.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")

    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")

    // Retrofit
    api("com.squareup.retrofit2:retrofit:3.0.0")
    api("com.squareup.retrofit2:converter-gson:3.0.0")
    api("com.squareup.okhttp3:logging-interceptor:5.5.1")

    // DataStore
    api("androidx.datastore:datastore-preferences:1.3.0")

    // image compression
    implementation("id.zelory:compressor:3.0.1")
}