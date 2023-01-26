plugins {
    id("tydev.android.library")
    id("tydev.android.hilt")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(project(":core"))
}
