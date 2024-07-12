package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Printer {
    private final char white;
    private final char black;
    private final BufferedImage img;

    public Printer(char white, char black, String filepath) throws IOException {
        this.white = white;
        this.black = black;
        this.img = ImageIO.read(new File(filepath));
    }

    public void draw() {
        int width = img.getWidth();
        int height = img.getHeight();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int sym = img.getRGB(j, i);

                if (sym == Color.WHITE.getRGB())
                    System.out.print(white);
                else
                    System.out.print(black);
            }
            System.out.println();
        }
    }
}
