plugins {
    id("tydev.android.feature")
    id("tydev.android.library.jacoco")
}

dependencies {
    implementation(libs.coil.kt.gif)

    implementation(project(":onboarding:onboarding-domain"))
    implementation(project(":di"))
}
