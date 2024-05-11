plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.tradecalc.lernjava"
    compileSdk = 34

    buildFeatures{
        viewBinding = true
    }


    defaultConfig {
        applicationId = "com.tradecalc.lernjava"
        minSdk = 27
        targetSdk = 34
        versionCode = 3
        versionName = "1.3"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //Открытие картинок с интернета способ 1
    implementation("com.android.volley:volley:1.2.1")
    //Парсинг
    implementation("org.jsoup:jsoup:1.17.2")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}