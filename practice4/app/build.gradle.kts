plugins {
    id("java")
    id("application")
}

group = "ua.edu.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
application {
    mainClass.set("ua.edu.ukma.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":processor"))
    annotationProcessor(project(":processor"))
}

tasks.test {
    useJUnitPlatform()
}

