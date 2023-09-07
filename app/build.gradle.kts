import com.tydev.base.TyBuildType
import java.util.Properties

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
        versionCode = 3
        versionName = "0.0.2" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val localProperties = Properties()
            val localPropertiesFile = rootProject.file("local.properties").inputStream()
            localProperties.load(localPropertiesFile)

            storeFile = file(localProperties["STORE_FILE"]!!)
            storePassword = localProperties["STORE_PASSWORD"] as String?
            keyPassword = localProperties["KEY_PASSWORD"] as String?
            keyAlias = localProperties["KEY_ALIAS"] as String?
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = TyBuildType.DEBUG.applicationIdSuffix
            signingConfig = signingConfigs.getByName("debug")
        }
        val release by getting {
            isMinifyEnabled = true
            applicationIdSuffix = TyBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        val benchmark by creating {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = TyBuildType.BENCHMARK.applicationIdSuffix
        }
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

    lint {
        baseline = file("lint-baseline.xml")
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

tasks.register("Release") {
    dependsOn(tasks["clean"])
    dependsOn(tasks["bundleRelease"])
    mustRunAfter(tasks["clean"])
}
