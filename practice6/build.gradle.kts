plugins {
    id("java")
    id("checkstyle")
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/stasyzar/Automation-of-work-with-software-projects-in-Java")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

checkstyle {
    toolVersion = "10.12.4"
    isIgnoreFailures = false
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
