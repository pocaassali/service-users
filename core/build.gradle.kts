plugins {
    kotlin("jvm")
}

group = "com.poc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.7")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<Test> {
    jvmArgs = listOf("-Dnet.bytebuddy.experimental=true")
}

kotlin {
    jvmToolchain(21)
}