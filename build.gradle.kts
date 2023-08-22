// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
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
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
}

apply {
    from("gradle/projectDependencyGraph.gradle")
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
