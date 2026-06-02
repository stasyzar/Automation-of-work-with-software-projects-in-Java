package ua.edu.ukma;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateDockerfileTask extends DefaultTask {
    public GenerateDockerfileTask() {
        setGroup("docker");
        setDescription("Generates a Dockerfile for the application.");
    }

    @TaskAction
    public void generateDockerfile() {
        File dockerfile = getProject().file("Dockerfile");

        if(dockerfile.exists()){
            getLogger().lifecycle("Dockerfile already exists. Skipping generation.");
            return;
        }

        try(FileWriter writer = new FileWriter(dockerfile)){
            writer.write("FROM eclipse-temurin:21-jre-alpine\n");
            writer.write("WORKDIR /app\n");
            writer.write("COPY build/libs/*.jar app.jar\n");
            writer.write("ENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]\n");
            getLogger().lifecycle("Dockerfile generated successfully.");
        } catch (IOException e) {
            getLogger().error("Failed to generate Dockerfile: {}", e.getMessage());
        }
    }
}

