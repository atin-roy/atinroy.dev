// Kotlin DSL is the modern, statically typed alternative to Groovy-based `build.gradle`.
// Comments explain how each block maps to the familiar Maven concepts you already know.
plugins {
    java // provides the standard Java lifecycle tasks similar to Maven's default lifecycle + compiler plugin
    id("org.springframework.boot") version "4.0.1" // Spring Boot plugin acts like spring-boot-starter-parent + run/packaging goals
    id("io.spring.dependency-management") version "1.1.7" // offers BOM-style dependencyManagement control just like Maven
}

// Coordinates correspond to Maven's <groupId>, <artifactId>, and <version>
group = "dev.atinroy"
version = "0.0.1-SNAPSHOT"
description = "In-memory lab notes backend"

// Toolchains ensure Gradle compiles with a known JDK version, similar to setting <maven.compiler.release>
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

// Repository declarations mirror Maven's <repositories>
repositories {
    mavenCentral()
}

// Dependencies use scoped DSL entries (implementation/testImplementation) akin to Maven scopes
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Gradle tasks can be configured directly; this block is equivalent to configuring Surefire/Failsafe to use JUnit Platform
tasks.named<Test>("test") {
    useJUnitPlatform()
}
