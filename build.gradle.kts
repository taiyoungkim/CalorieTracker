// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()

        // Android Build Server
//        maven { url = uri("../nowinandroid-prebuilts/m2repository") }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.code.ktlint)
    alias(libs.plugins.code.detekt)
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.code.ktlint.get().pluginId)
        plugin(rootProject.libs.plugins.code.detekt.get().pluginId)
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = false
        config = files("$rootDir/detekt.yml")
    }
}
