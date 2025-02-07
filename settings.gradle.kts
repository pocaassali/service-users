plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "service-users"
include("core")
include("infrastructure")
include("app")
