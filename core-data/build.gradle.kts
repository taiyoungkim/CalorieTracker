plugins {
    id("tydev.android.library")
    id("tydev.android.hilt")
}

dependencies {
    implementation(project(":datastore"))
    implementation(project(":core"))
}
