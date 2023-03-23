package org.cis1200;

/*
 * This file is adapted by UPenn CIS 120 course staff from code by
 * Richard Wicentowski and Tia Newhall (2005)
 * Modified by Fernando Pereira to accept URLs and other resource locators
 * 
 * You do not need to modify this file.
 */
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * An image represented by a 2D array of Pixels.
 *
 * PixelPictures are immutable. Although they provide access to a 2D
 * array of pixels, this array is a copy of the one stored in the NewPic.
 * The original image cannot be modified.
 */
public class PixelPicture {

    private BufferedImage bufferedImage;
    private WritableRaster raster;

    /**
     * Copies a NewPic.
     * 
     * @param other NewPic the other NewPic to copy
     */
    public PixelPicture(PixelPicture other) {
        bufferedImage = new BufferedImage(
                other.getWidth(),
                other.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        raster = bufferedImage.getRaster();
        raster.setRect(other.bufferedImage.getRaster());
    }

    /**
     * Creates a NewPic by loading the given file or URL.
     * 
     * @param filename the location of the image file to read
     */
    public PixelPicture(String filename) {
        load(filename);
    }

    /**
     * Creates a picture given a bitmap. The bitmap should be in left-to-right,
     * top-to-bottom ordering.
     * 
     * @param bmp The bitmap
     */
    public PixelPicture(Pixel[][] bmp) {
        setBitmap(bmp);
    }

    /**
     * @return the width of the image.
     */
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    /**
     * @return the height of the image.
     */
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    private void load(String filename) {
        ImageIcon icon;

        try {
            if ((new File(filename)).exists()) {
                icon = new ImageIcon(filename);
            } else {
                java.net.URL u = new java.net.URL(filename);
                icon = new ImageIcon(u);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Image image = icon.getImage();
        bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_RGB
        );
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        raster = bufferedImage.getRaster();
    }

    private void setBitmap(Pixel[][] bmp) {
        int h = bmp.length;

        if (h == 0) {
            throw new IndexOutOfBoundsException("expected non-empty image, got height of 0");
        }

        int w = bmp[0].length;
        if (w == 0) {
            throw new IndexOutOfBoundsException("expected non-empty image, got width of 0");
        }

        bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        raster = bufferedImage.getRaster();

        for (int y = 0; y < h; y++) {
            if (bmp[y].length != w) {
                throw new IndexOutOfBoundsException(
                        "ragged image, found width of " + w + " and " + bmp[y].length
                );
            }

            for (int x = 0; x < w; x++) {
                if (bmp[y][x].getComponents() == null) {
                    throw new UnsupportedOperationException("please implement the Pixel class");
                }
                raster.setPixel(x, y, bmp[y][x].getComponents());
            }
        }
    }

    /**
     * Gets a bitmap (i.e., matrix of pixels) of the image. This method returns
     * a copy of the image's contents---editing the returned bitmap will not
     * affect the NewPic.
     * 
     * The bitmap is in a top-to-bottom, left-to-right order. The first index is
     * the row, the second index is the column. That way, a pixel at coordinate
     * (x,y) can be accessed as bmp[y][x].
     * 
     * @return a top-to-bottom, left-to-right order array of arrays of Pixels
     */
    public Pixel[][] getBitmap() {
        int w = getWidth();
        int h = getHeight();

        Pixel[][] bmp = new Pixel[h][w];

        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                bmp[row][col] = new Pixel(raster.getPixel(col, row, (int[]) null));
            }
        }

        return bmp;
    }

    /**
     * Creates an ImageIcon, suitable for display by Swing components.
     * 
     * @return ImageIcon pointing at a copy of this image
     */
    public ImageIcon toImageIcon() {
        PixelPicture copy = new PixelPicture(this);
        return new ImageIcon(copy.bufferedImage);
    }

    /**
     * Compute the difference between two images.
     *
     * This difference sums the pixel-by-pixel differences
     * between components of a pixel. It is most useful
     * for SMALL images.
     * 
     * @param p0 first image to compare
     * @param p1 second image to compare
     * @return sum of all differences between pixel components
     */
    public static int diff(PixelPicture p0, PixelPicture p1) {

        if (p0.getHeight() != p1.getHeight() || p0.getWidth() != p1.getWidth()) {
            return Integer.MAX_VALUE;
        }
        int diff = 0;
        Pixel[][] b0 = p0.getBitmap();
        Pixel[][] b1 = p1.getBitmap();
        int w = p0.getWidth();
        int h = p0.getHeight();
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Pixel pix0 = b0[row][col];
                Pixel pix1 = b1[row][col];
                diff += Math.abs(pix0.getRed() - pix1.getRed()) +
                        Math.abs(pix0.getBlue() - pix1.getBlue()) +
                        Math.abs(pix0.getGreen() - pix1.getGreen());
            }
        }
        return diff;
    }

    /**
     * Print all of the pixels in the image to the console.
     *
     * This is debugging aid. Only use this method for SMALL images.
     */
    public void print() {
        Pixel[][] bmp = getBitmap();
        int w = getWidth();
        int h = getHeight();
        for (int i = 0; i < w; i++) {
            System.out.print("{");
            for (int j = 0; j < h; j++) {
                System.out.print("new Pixel");
                System.out.print(bmp[j][i]);
                if (j != h - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("}");
        }
    }

    private static final Pattern SUFFIX = Pattern.compile(".*\\.(\\w{3,4})");

    public void save(String filename) {
        String type = "png";

        // detect the file type
        Matcher m = SUFFIX.matcher(filename);
        if (m.matches()) {
            type = m.group(1);
        }

        try {
            ImageIO.write(bufferedImage, type, new File(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
