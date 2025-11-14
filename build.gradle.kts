plugins {
    java
    id("jacoco")
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "br.com.wisefinance"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    destinationDirectory.set(file("${projectDir}/dist"))
}

tasks.named<Jar>("jar") {
    enabled = false
}

configurations.configureEach {
    exclude(group = "ch.qos.logback", module = "logback-classic")
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

repositories {
    mavenCentral()
}

dependencies {

    // Flyway and PostgreSQL
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    // Spring Boot
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Tests
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}