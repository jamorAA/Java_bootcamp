package edu.school21.spring.program;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.school21.spring.preprocessor.PreProcessor;
import edu.school21.spring.preprocessor.PreProcessorToUpperImpl;
import edu.school21.spring.printer.Printer;
import edu.school21.spring.printer.PrinterWithPrefixImpl;
import edu.school21.spring.renderer.Renderer;
import edu.school21.spring.renderer.RendererStandardImpl;

public class Program {
    public static void main(String[] args) {
        PreProcessor pp = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererStandardImpl(pp);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer2 = context.getBean("printerWithPrefix", Printer.class);
        printer2.print("Hello!");
    }
}
