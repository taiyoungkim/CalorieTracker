pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CalorieTracker"
include(":app")
include(":core")
include(":core-ui")
include(":onboarding")
include(":onboarding:onboarding-domain")
include(":onboarding:onboarding-presentation")
include(":tracker")
include(":tracker:tracker-domain")
include(":tracker:tracker-data")
include(":tracker:tracker-presentation")
include(":datastore")
include(":di")
include(":benchmark")
