package edu.school21.printer.app;
import edu.school21.printer.logic.Printer;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        if (args.length != 3 || args[0].length() != 1 || args[1].length() != 1) {
            System.out.println("Error");
            System.exit(-1);
        }
        try {
            Printer printer = new Printer(args[0].charAt(0), args[1].charAt(0), args[2]);
            printer.draw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
