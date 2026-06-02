package ua.edu.ukma;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class GenerateReportTask extends DefaultTask {

    public GenerateReportTask() {
        setGroup("report");
        setDescription("Generates a report for the application.");
    }

    @TaskAction
    public void generateReport() {
        File buildDir = getProject().getLayout().getBuildDirectory().get().getAsFile();
        File reportFile = new File(buildDir, "report.txt");

        try{
            Files.createDirectories(buildDir.toPath());

            try(FileWriter writer = new FileWriter(reportFile)){
                writer.write("Application Report\n");
                writer.write("=================\n");
                writer.write("Project Name: " + getProject().getName() + "\n");
                writer.write("Version: " + getProject().getVersion() + "\n");
                writer.write("Generated on: " + java.time.LocalDateTime.now() + "\n");

                getLogger().lifecycle("Report generated successfully at: " + reportFile.getAbsolutePath());
            } catch (IOException e){
                getLogger().error("Failed to generate report: {}", e.getMessage());
            }
        } catch (IOException e){
            getLogger().error("Failed to create build directory: {}", e.getMessage());
        }
    }
}
