plugins {
    id("tydev.android.library")
    id("tydev.android.library.compose")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-ui"))

    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
}