plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    alias(libs.plugins.compose.compiler)
}


android {
    namespace = "com.example.newfoodme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.newfoodme"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose dependencies
    implementation(platform(libs.androidx.compose.bom)) // Compose BOM (Bill of Materials)
    implementation(libs.androidx.compose.material3) // Material 3 for newer components
    implementation(libs.androidx.compose.material) // Material for older components
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

dependencies {
    // Andere Abhängigkeiten ...

    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
}

dependencies {
    // Andere Abhängigkeiten ...

    implementation("com.google.android.gms:play-services-location:21.3.0") // Aktuellste Version überprüfen
}

dependencies {
    // Andere Abhängigkeiten ...

    implementation("com.google.maps.android:maps-compose:6.1.0") // Aktuellste Version überprüfen
}

dependencies {
    // Google Maps and Places
    implementation (libs.google.maps.sdk)
    // Jetpack Compose for Google Maps
    implementation ("com.google.maps.android:maps-compose:2.11.3")

    // Other dependencies...
}

dependencies {
    implementation("com.google.android.gms:play-services-location:21.3.0")
}
dependencies {
    implementation ("com.google.maps.android:maps-utils-ktx:2.2.0") // falls du zusätzliche Funktionen benötigst
}

dependencies {
    implementation(libs.places)// Jetpack Compose UI dependencies
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")

    // Required for Compose's preview and tooling support
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")

    // Required for Activity support with Compose
    implementation("androidx.activity:activity-compose:1.8.0")

    // Optional: Icons and more Material components
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    implementation ("androidx.compose.ui:ui:1.0.0") // Verwende die passende Version für dein Projekt

}

dependencies {
    implementation("com.google.android.gms:play-services-maps:18.1.0")
}
dependencies {
    implementation("com.google.android.libraries.places:places:3.1.0")
}
dependencies {
    implementation("androidx.fragment:fragment-ktx:1.6.0")
}
dependencies {
    implementation ("com.google.android.libraries.places:places:latest_version")
}
dependencies {
    implementation (libs.androidx.fragment)
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

}
