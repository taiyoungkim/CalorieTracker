plugins {
    id("tydev.android.library")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    implementation(project(":core"))
}
