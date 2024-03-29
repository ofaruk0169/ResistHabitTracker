plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.resisthabitapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.resisthabitapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //ktor api req
    //implementation("io.ktor:ktor-server-core:2.3.7")
    //implementation("io.ktor:ktor-server-netty:2.3.7")
    //implementation ("io.ktor:ktor-client-json:2.3.7")

    implementation ("androidx.fragment:fragment-ktx:1.3.0") // or the latest version

    //using datastore as shared preferences is deprecated
    implementation("androidx.datastore:datastore-preferences:1.0.0")



    implementation ("io.ktor:ktor-client-core:2.3.7")
    implementation ("io.ktor:ktor-client-json:2.3.7")
    implementation ("io.ktor:ktor-client-android:2.3.7")
    implementation ("io.ktor:ktor-client-logging:2.3.7")
    implementation ("ch.qos.logback:logback-classic:1.2.3")

    val activityVersion = "1.8.2"
    implementation("androidx.activity:activity-ktx:$activityVersion")

    val lifecycleVersion = "2.6.2"

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}