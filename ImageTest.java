package org.cis120;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This file tests the various image manipulations on the default
 * image (a view of Positano, Italy).
 *
 * Note though that due to the imprecise nature of floating point
 * arithmetic, your code may be correct yet not pass these tests. (We
 * won't be using these tests to grade your assignment! Our tests take
 * this imprecision into account.) Instead, these tests give you an
 * idea of how close your answers are to *our* solution. Just looking
 * at the output of your code in the GUI may not be enough to see the
 * differences.
 *
 * This file also gives you a place to add your own JUnit test cases.
 * Do *not* add them to ManipulateTest.java as you will not be
 * submitting that file.
 */

public class ImageTest {

    /*
     * IMPORTANT: Make sure that this points to the correct directory before
     * running the tests! You will need to download and extract the provided
     * images from the homework instructions for this to work. (And don't forget
     * that the string should end with a '/' character!)
     */
    static final String LOCATION = "images/";

    static final PixelPicture ITALY = new PixelPicture(LOCATION + "Italy.png");

    @Test
    public void testRotateCW() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyCW.png"),
                        SimpleManipulations.rotateCW(ITALY)
                ),
                "Rotate CW"
        );
    }

    @Test
    public void testRotateCCW() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyCCW.png"),
                        SimpleManipulations.rotateCCW(ITALY)
                ),
                "Rotate CCW"
        );
    }

    @Test
    public void testBorder() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyBorder.png"),
                        SimpleManipulations.border(ITALY, 10, Pixel.BLACK)
                ),
                "Border"
        );
    }

    @Test
    public void testColorInvert() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyColorInvert.png"),
                        SimpleManipulations.invertColors(ITALY)
                ),
                "ColorInversion"
        );
    }

    @Test
    public void testGrayScaleAverage() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyGrayScaleAverage.png"),
                        SimpleManipulations.grayScaleAverage(ITALY)
                ),
                "Gray Scale Average"
        );
    }

    @Test
    public void testColorScale() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyRedTint.png"),
                        SimpleManipulations.scaleColors(ITALY, 1.0, 0.5, 0.5)
                ),
                "Color Scale"
        );
    }

    @Test
    public void testAlphaBlend() {
        PixelPicture p = new PixelPicture(LOCATION + "ItalyGrayScaleAverage.png");
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyBlendGrayScaleAvg.png"),
                        SimpleManipulations.alphaBlend(0.3, ITALY, p)
                ),
                "alpha-Blend"
        );
    }

    @Test
    public void testContrast() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyContrast2.png"),
                        AdvancedManipulations.adjustContrast(ITALY, 2.0)
                ),
                "Contrast"
        );
    }

    @Test
    public void testReducePalette() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyRP512.png"),
                        AdvancedManipulations.reducePalette(ITALY, 512)
                ),
                "Reduce Palette 512"
        );
    }

    @Test
    public void testVignette() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyVignette.png"),
                        SimpleManipulations.vignette(ITALY)
                ),
                "Vignette"
        );
    }

    @Test
    public void testBlur() {
        assertEquals(
                0,
                PixelPicture.diff(
                        new PixelPicture(LOCATION + "ItalyBlur2.png"),
                        AdvancedManipulations.blur(ITALY, 2)
                ),
                "blur 2"
        );
    }

}
