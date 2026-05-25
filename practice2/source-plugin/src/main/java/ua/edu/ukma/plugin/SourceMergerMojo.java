package ua.edu.ukma.plugin;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Mojo(name="merge-sources", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class SourceMergerMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true, required = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException{
        if(!sourceDirectory.exists()){
            getLog().warn("Source directory does not exist: " + sourceDirectory.getAbsolutePath());
            return;
        }
        File outputFile = new File(outputDirectory, "merged-sources.txt");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            getLog().info("Починаю збирати код з папки: " + sourceDirectory.getAbsolutePath());

            try(Stream<Path> paths = Files.walk(sourceDirectory.toPath())) {
                paths.filter(Files::isRegularFile)
                     .filter(path -> path.toString().endsWith(".java"))
                     .forEach(path -> {
                         try {
                             writer.write("\n// " + "=".repeat(40) + "\n");
                             writer.write("// ФАЙЛ: " + path.getFileName() + "\n");
                             writer.write("// " + "=".repeat(40) + "\n\n");

                             writer.write(Files.readString(path));
                             writer.write("\n");
                         } catch (IOException e) {
                             getLog().error("Помилка читання файлу: " + path.getFileName(), e);
                         }
                     });
            }
            getLog().info("Весь код зібрано у файл: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            throw new MojoExecutionException("Error merging sources", e);
        }
    }
}
