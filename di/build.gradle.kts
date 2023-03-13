plugins {
    id("tydev.android.library")
    id("tydev.android.library.jacoco")
    id("tydev.android.hilt")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":datastore"))
    implementation(project(":tracker::tracker-domain"))
    implementation(project(":tracker::tracker-data"))
    implementation(project(":onboarding:onboarding-domain"))
}
