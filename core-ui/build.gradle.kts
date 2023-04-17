plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.library.compose")
}

dependencies {
    implementation(libs.androidx.compose.ui.unit)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.core.ktx)
}
