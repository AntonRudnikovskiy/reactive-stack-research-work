plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.losevskiyfz"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    dependencies {
        implementation("com.github.javafaker:javafaker:1.0.2") {
            exclude(module = "org.yaml")
        }
        implementation("org.yaml:snakeyaml:1.26")
    }
    implementation("org.mapstruct:mapstruct:1.6.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.3.3")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
