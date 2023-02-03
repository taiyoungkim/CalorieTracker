plugins {
    id("tydev.android.library")
    id("tydev.android.library.compose")
    id("tydev.android.hilt")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core"))
    implementation(project(":onboarding:onboarding-domain"))

    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)
}
