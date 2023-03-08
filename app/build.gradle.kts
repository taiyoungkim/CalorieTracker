import com.tydev.base.TyBuildType

plugins {
    id("tydev.android.application")
    id("tydev.android.application.compose")
    id("tydev.android.application.jacoco")
    id("tydev.android.hilt")
    id("jacoco")
    id("tydev.firebase-perf")
}

android {
    defaultConfig {
        applicationId = "com.tydev.calorietracker"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = TyBuildType.DEBUG.applicationIdSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = TyBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.getByName("debug")
        }
//        val benchmark by creating {
//            // Enable all the optimizations from release build through initWith(release).
//            initWith(release)
//            matchingFallbacks.add("release")
//            // Debug key signing is available to everyone.
//            signingConfig = signingConfigs.getByName("debug")
//            // Only use benchmark proguard rules
//            proguardFiles("benchmark-rules.pro")
//            isMinifyEnabled = true
//            applicationIdSuffix = TyBuildType.BENCHMARK.applicationIdSuffix
//        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.tydev.calorietracker"
}

dependencies {
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)

    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":onboarding:onboarding-presentation"))
    implementation(project(":tracker:tracker-presentation"))
}

// androidx.test is forcing JUnit, 4.12. This forces it to use 4.13
configurations.configureEach {
    resolutionStrategy {
        force(libs.junit4)
        // Temporary workaround for https://issuetracker.google.com/174733673
        force("org.objenesis:objenesis:2.6")
    }
}
