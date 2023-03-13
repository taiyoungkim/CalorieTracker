plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
}

dependencies {
    implementation(project(":core"))
}
