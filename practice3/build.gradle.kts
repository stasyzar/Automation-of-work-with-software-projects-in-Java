plugins {
    java
    id("ua.edu.ukma.docker-setup")
}
group = "ua.edu.ukma"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.register<Zip>("packageHomework") {
    group = "homework"
    description = "Package the working directory"
    archiveFileName.set("practice3_Zarovska.zip")
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))

    from(layout.projectDirectory) {
        exclude("build/**")
        exclude(".gradle/**")
        exclude(".idea/**")
        exclude("buildSrc/build/**")
        exclude("buildSrc/.gradle/**")
    }
}

tasks.test {
    useJUnitPlatform()
}