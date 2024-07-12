package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Printer {
    private final String white;
    private final String black;
    private final BufferedImage img;

    public Printer(Param args, String filepath) throws IOException {
        this.white = args.getWhite();
        this.black = args.getBlack();
        this.img = ImageIO.read(Objects.requireNonNull(Printer.class.getResource(filepath)));
    }

    public void print() {
        ColoredPrinter printer = new ColoredPrinter();
        int width = img.getWidth();
        int height = img.getHeight();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int sym = img.getRGB(j, i);

                if (sym == Color.WHITE.getRGB())
                    printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(white));
                else if (sym == Color.BLACK.getRGB())
                    printer.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(black));
            }
            System.out.println();
        }
    }
}
