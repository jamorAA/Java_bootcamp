package edu.school21.Annotation.Process;

import com.google.auto.service.AutoService;
import edu.school21.Annotation.Annotations.HtmlForm;
import edu.school21.Annotation.Annotations.HtmlInput;
import javax.annotation.processing.*;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.tools.StandardLocation;
import java.io.IOException;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;

@SupportedAnnotationTypes("edu.school21.Annotation.Annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_18)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            List<? extends Element> elements = element.getEnclosedElements();
            List<Annotation> fields = new ArrayList<>(elements.size());
            for (Element element1 : elements) {
                if (element1.getAnnotation(HtmlInput.class) != null) {
                    Annotation field = element1.getAnnotation(HtmlInput.class);
                    fields.add(field);
                }
            }
            createHTML(element.getAnnotation(HtmlForm.class), fields);
        }
        return true;
    }
    public void createHTML(HtmlForm form, List<Annotation> fields) {
        try {
            FileObject fo = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", form.fileName());
            try (PrintWriter pw = new PrintWriter(fo.openWriter())) {
                pw.println(String.format("<form action = \"%s\" method = \"%s\">", form.action(), form.method()));
                for (Annotation field : fields) {
                    HtmlInput input = (HtmlInput)field;
                    pw.println(String.format("<input type = \"%s\" name = \"%s\" placeholder = \"%s\">", input.type(), input.name(), input.placeholder()));
                }
                pw.println("<input type = \"submit\" value = \"Send\">");
                pw.println("</form>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
