package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.Param;
import edu.school21.printer.logic.Printer;

public class Program {
    public static void main(String[] args) {
        try {
            Param param = new Param();
            JCommander.newBuilder().addObject(param).build().parse(args);
            Printer printer = new Printer(param, "C:\\Users\\User\\Java_Bootcamp.Day04-1\\src\\ex01\\ImageToChar\\src\\resources\\image.bmp");
            printer.print();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
