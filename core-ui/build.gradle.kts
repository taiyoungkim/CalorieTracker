plugins {
    id("tydev.android.library")
    id("tydev.android.library.compose")
}

dependencies {
    implementation(libs.androidx.compose.ui.unit)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.ui.tooling)
}
