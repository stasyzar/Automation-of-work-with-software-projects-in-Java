plugins {
    java
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("dockerSetup") {
            id = "ua.edu.ukma.docker-setup"
            implementationClass = "ua.edu.ukma.DockerSetupPlugin"
        }
    }
}