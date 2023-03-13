plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
    id("kotlinx-serialization")
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    api(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(project(":core"))
    implementation(project(":tracker:tracker-domain"))

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
