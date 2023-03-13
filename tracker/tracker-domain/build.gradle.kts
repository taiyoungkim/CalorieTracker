plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(project(":core"))
}
