plugins {
    id("java")
    id("org.springframework.boot") version "3.2.0" // Add Spring Boot plugin
    id("io.spring.dependency-management") version "1.1.4" // Add dependency management
}

group = "org.smarthome"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Other dependencies (like spring-boot-starter-web) will be here...

    // Database access: Spring Data JPA (includes Jakarta Persistence)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Database driver for PostgreSQL
    runtimeOnly("org.postgresql:postgresql")

    // Lombok for generating boilerplate code (Getters/Setters/etc.)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Testing dependencies go here...

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

tasks.test {
    useJUnitPlatform()
}