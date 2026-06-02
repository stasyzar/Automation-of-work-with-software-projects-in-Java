package ua.edu.ukma;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DockerSetupPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("generateDockerIgnore", GenerateDockerIgnoreTask.class);
        project.getTasks().register("generateReport", GenerateReportTask.class);
        project.getTasks().register("generateDockerfile", GenerateDockerfileTask.class);
    }
}
