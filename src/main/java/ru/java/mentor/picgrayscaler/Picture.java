package ru.java.mentor.picgrayscaler;

import ru.java.mentor.picgrayscaler.exceptions.NotImageException;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Picture {
    public Path getPath() {
        return path;
    }

    private final Path path;
    private BufferedImage originalImage;
    private BufferedImage grayScaledImage;

    public Picture(Path path) {
        this.path = path;
    }

    public BufferedImage getGrayScaledImage() throws IOException, NotImageException {
        if ( grayScaledImage != null) {
            return grayScaledImage;
        }
        BufferedImage image = loadImage();
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_RGB) ;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                grayImage.setRGB(i, j, calcGrayRGBAtPoint(i, j));
            }
        }
        this.grayScaledImage = grayImage;
        return grayImage;
    }

    private BufferedImage loadImage() throws IOException, NotImageException {
        BufferedImage img = ImageIO.read(path.toFile());
        if (img == null) {
            throw new NotImageException();
        }
        this.originalImage = img;
        return img;
    }

    private int calcGrayRGBAtPoint(int x, int y){
        Color originalColor = new Color(this.originalImage.getRGB(x, y));
        int grayLevel = (originalColor.getBlue() + originalColor.getGreen() + originalColor.getRed()) / 3;
        return new Color(grayLevel, grayLevel, grayLevel).getRGB();
    }
}
