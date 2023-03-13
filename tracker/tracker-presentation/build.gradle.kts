plugins {
    id("tydev.android.feature")
    id("tydev.android.library.jacoco")
}

dependencies {
    implementation(project(":tracker::tracker-domain"))
    implementation(project(":di"))
}
