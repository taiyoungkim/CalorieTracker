plugins {
    id("tydev.android.feature")
}

dependencies {
    implementation(project(":tracker::tracker-domain"))
    implementation(project(":tracker::tracker-data"))
}
