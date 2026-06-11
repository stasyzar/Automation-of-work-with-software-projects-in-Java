plugins {
    id("java")
}

group = "ua.edu.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:6.0.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("testFastSuite") {
    group = "verification"
    useJUnitPlatform {
        includeTags("fast")
    }
}

tasks.register<Test>("testSlowSuite") {
    group = "verification"
    useJUnitPlatform {
        includeTags("slow")
    }
}