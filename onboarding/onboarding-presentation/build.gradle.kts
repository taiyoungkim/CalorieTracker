plugins {
    id("tydev.android.feature")
}

dependencies {
    implementation(libs.coil.kt.gif)

    implementation(project(":onboarding:onboarding-domain"))
    implementation(project(":di"))
}
