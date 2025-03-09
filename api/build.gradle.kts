plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.poc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    testImplementation(project(":app"))
    testImplementation(kotlin("test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.4") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.springframework.security:spring-security-test:6.1.4")
    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.test {
    useJUnitPlatform()
}
tasks.bootJar{
    enabled = false
}

kotlin {
    jvmToolchain(21)
}