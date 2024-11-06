plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5" apply false
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.caju"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
    implementation( project(":domain") )
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.5")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.3")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}