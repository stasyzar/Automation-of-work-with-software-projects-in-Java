package ua.edu.ukma;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Mojo(name="check-code", defaultPhase= LifecyclePhase.VERIFY)
public class CodeCheckerMojo extends AbstractMojo {

    @Parameter(defaultValue="${project.build.sourceDirectory}", readonly=true, required=true)
    private File sourceDirectory;

    @Parameter(property="forbiddenKeyword", defaultValue="System.out.println")
    private String forbiddenKeyword;

    @Override
    public void execute() throws MojoExecutionException {
        if(!sourceDirectory.exists()) {
            getLog().warn("Source directory does not exist: " + sourceDirectory);
            return;
        }
        getLog().info( " Checking for forbidden keyword: '" + forbiddenKeyword + "'");
        checkDirectory(sourceDirectory);
    }

    private void checkDirectory(File directory) throws MojoExecutionException {
        File[] files = directory.listFiles();
        if(files == null) return;

        for(File file : files) {
            if(file.isDirectory()) {
                checkDirectory(file);
            } else if(file.getName().endsWith(".java")) {
                checkFile(file);
            }
        }
    }

    private void checkFile(File file) throws MojoExecutionException {
        try{
            boolean inBlockComment = false;

            List<String> lines = Files.readAllLines(file.toPath());
            for(int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (line.startsWith("/*")) {
                    inBlockComment = true;
                }
                if (line.endsWith("*/") || line.contains("*/")) {
                    inBlockComment = false;
                    continue;
                }
                if (inBlockComment) {
                    continue;
                }
                if (line.startsWith("//")) {
                    continue;
                }

                if(line.contains(forbiddenKeyword)) {
                   throw new MojoExecutionException("Forbidden keyword: '" + forbiddenKeyword + "' in a file " + file.getName() + " on row " + (i + 1));
                }
            }
        } catch (IOException  e) {
            throw new MojoExecutionException("Failed to read file: " + file, e);
        }
    }
}
