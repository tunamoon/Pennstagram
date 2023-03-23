package org.cis1200;

/**
 * This class defines the top-level image effects found on
 * the right-hand side of the GUI.
 * 
 * Edit the static method "custom" if you would like to create your
 * own custom effect.
 * 
 */
public class Effects {

    public static PixelPicture eighteenNinety(PixelPicture p) {
        // create a gray-scale version of the image
        PixelPicture gray = SimpleManipulations.grayScaleLuminosity(p);
        // color the gray-scale version in a sepia tone
        PixelPicture sepia = SimpleManipulations.scaleColors(gray, 1.0, 0.95, 0.75);
        // add vignetting, blend in so not so intense
        PixelPicture vign = SimpleManipulations.vignette(sepia);
        PixelPicture blend = SimpleManipulations.alphaBlend(0.5, sepia, vign);

        // increase the brightness of the image to counteract
        // the vignetting
        return SimpleManipulations.scaleColors(blend, 1.1, 1.1, 1.1);
    }

    public static PixelPicture pinHole(PixelPicture p) {
        PixelPicture p1 = SimpleManipulations.scaleColors(p, 1.0, 0.95, 0.75);
        PixelPicture p2 = SimpleManipulations.scaleColors(p1, 1.1, 1.1, 1.0);
        PixelPicture p3 = SimpleManipulations.vignette(p2);
        PixelPicture p4 = AdvancedManipulations.blur(p3, 1);
        return SimpleManipulations.border(
                p4, 10,
                new Pixel(255, 255, 255)
        );
    }

    public static PixelPicture zombie(PixelPicture p) {
        // desaturate by blending in grayscale version
        PixelPicture gray = SimpleManipulations.grayScaleLuminosity(p);
        PixelPicture p1 = SimpleManipulations.alphaBlend(0.2, p, gray);

        // add purplish cast
        PixelPicture purples = SimpleManipulations
                .scaleColors(gray, 34.0 / 255, 43.0 / 255, 109.0 / 255);
        p1 = SimpleManipulations.alphaBlend(0.7, p1, purples);

        // up the contrast
        p1 = AdvancedManipulations.adjustContrast(p1, 3.0);

        // put a black border around it
        p1 = SimpleManipulations.border(p1, 20, new Pixel(0, 0, 0));
        return p1;
    }

    public static PixelPicture plastic(PixelPicture p) {
        // significantly increase the contrast
        PixelPicture p1 = AdvancedManipulations.adjustContrast(p, 3.0);
        // add a vignette
        p1 = SimpleManipulations.vignette(p1);
        return p1;
    }

    public static PixelPicture peaches(PixelPicture p) {
        // Make everything brighter
        PixelPicture p1 = SimpleManipulations.scaleColors(p, 1.2, 1.2, 1.2);

        // reduce the palette
        p1 = AdvancedManipulations.reducePalette(p1, 512);

        // add a peach tint to the image
        return SimpleManipulations.scaleColors(p1, 1.0, 229.0 / 255, 180.0 / 255);
    }

    // Create your own effect here! If it is cool,
    // feel free to post it to Piazza.
    public static PixelPicture custom(PixelPicture p) {
        return p;
    }

}
