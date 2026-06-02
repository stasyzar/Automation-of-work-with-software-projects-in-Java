package ua.edu.ukma;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GenerateDockerIgnoreTask extends DefaultTask {

    public GenerateDockerIgnoreTask() {
        setGroup("docker");
        setDescription("Generates a .dockerignore file for the application.");
    }

    @TaskAction
    public void generateDockerIgnore() throws IOException {
        File rootDir = getProject().getProjectDir();
        File ignoreFile = new File(rootDir, ".dockerignore");
        String content = "build/\n" +
                "out/\n" +
                "target/\n" +
                ".gradle/\n" +
                ".idea/\n" +
                "*.iml\n" +
                "*.log\n" +
                "*.tmp\n" +
                "node_modules/\n" +
                "dist/\n" +
                "coverage/\n";
        try{
            Files.writeString(ignoreFile.toPath(), content);
            getLogger().lifecycle(".dockerignore generated successfully at: " + ignoreFile.getAbsolutePath());
        } catch (IOException e) {
            getLogger().error("Failed to create docker directory: {}", e.getMessage());
        }
    }
}
