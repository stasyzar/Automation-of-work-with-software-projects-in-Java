package ua.edu.ukma;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("ua.edu.ukma.GenerateDto")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class DtoProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
       for(Element element : roundEnv.getElementsAnnotatedWith(GenerateDto.class)) {
           if(element.getKind() == ElementKind.CLASS) {
               TypeElement classElement = (TypeElement) element;
               String className = classElement.getSimpleName().toString();
               String packageName = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();
               generateDtoClass(packageName, className + "Dto", classElement);
           }
       }
       return true;
    }

    private void generateDtoClass(String packageName, String dtoClassName, TypeElement classElement) {
        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + dtoClassName);
            try (Writer writer = builderFile.openWriter()) {
                writer.write("package " + packageName + ";\n\n");
                writer.write("import ua.edu.ukma.NotNull;\n\n");
                writer.write("public class " + dtoClassName + " {\n");

                for (Element enclosed : classElement.getEnclosedElements()) {
                    if (enclosed.getKind() == ElementKind.FIELD) {
                        if (enclosed.getAnnotation(ExcludeFromDto.class) != null) {
                            continue;
                        }
                        writer.write("    @NotNull\n");
                        writer.write("    public " + enclosed.asType().toString() + " " + enclosed.getSimpleName() + ";\n\n");
                    }
                }
                writer.write("}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}