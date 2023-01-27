plugins {
    id("tydev.android.library")
    id("tydev.android.library.compose")
    id("tydev.android.hilt")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":core"))
    implementation(project(":core-ui"))
    implementation(project(":tracker::tracker-domain"))
}
