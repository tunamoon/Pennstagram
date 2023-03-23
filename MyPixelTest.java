package org.cis120;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Use this file to test your implementation of Pixel.
 * 
 * We will manually grade this file to give you feedback
 * about the completeness of your test cases.
 */

public class MyPixelTest {

    /*
     * Remember, UNIT tests should ideally have one point of failure. Below we
     * give you two examples of unit tests for the Pixel constructor, one that
     * takes in three ints as arguments and one that takes in an array. We use
     * the getRed(), getGreen(), and getBlue() methods to check that the values
     * were set correctly. These two tests do not comprehensively test all of
     * Pixel so you must add your own.
     * 
     * You might want to look into assertEquals, assertTrue, assertFalse, and
     * assertArrayEquals at the following:
     * http://junit.sourceforge.net/javadoc/org/junit/Assert.html
     *
     * Note, if you want to add global variables so that you can reuse Pixels
     * in multiple tests, feel free to do so.
     */

    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructArrayLongerThan3() {
        int[] arr = {10, 20, 30, 40};
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }
    @Test
    public void testConstructArrayShorterThan3() {
        int[] arr = {10};
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testColorLessThanZero() {
        int[] arr = {-1, 2, 5};
        Pixel p = new Pixel(arr);
        assertEquals(0, p.getRed());
        assertEquals(2, p.getGreen());
        assertEquals(5, p.getBlue());
    }

    @Test
    public void testColorMoreThan255() {
        int[] arr = {0, 2, 295};
        Pixel p = new Pixel(arr);
        assertEquals(0, p.getRed());
        assertEquals(2, p.getGreen());
        assertEquals(255, p.getBlue());
    }

    @Test
    public void testColorDistance() {
        int[] arr1 = {0, 2, 295};
        int[] arr2 = {10, 3, 200};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertEquals(66, p2.distance(p1));
    }

    @Test
    public void testDifferentPixel() {
        int[] arr1 = {0, 2, 295};
        int[] arr2 = {10, 3, 200};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        boolean a = false;
        assertEquals(a, p1.sameRGB(p2));
    }

    @Test
    public void testSamePixel() {
        int[] arr1 = {0, 2, 295};
        int[] arr2 = {0, 2, 255};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        boolean a = true;
        assertEquals(a, p1.sameRGB(p2));
    }

    //getRed
    @Test
    public void testGetRed() {
        int[] arr1 = {0, 2, 295};
        Pixel p1 = new Pixel(arr1);
        assertEquals(0, p1.getRed());
    }

    //getGreen
    @Test
    public void testGetGreen() {
        int[] arr1 = {0, 2, 295};
        Pixel p1 = new Pixel(arr1);
        assertEquals(2, p1.getGreen());
    }

    //getBlue
    @Test
    public void testGetBlue() {
        int[] arr1 = {0, 2, 255};
        Pixel p1 = new Pixel(arr1);
        assertEquals(255, p1.getBlue());
    }

    //getComponents test
    @Test
    public void testGetComponents() {
        int[] arr1 = {0, 2, 255};
        Pixel p1 = new Pixel(arr1);
        int[] test = p1.getComponents();
        assertEquals(arr1[0], test[0]);
        assertEquals(arr1[1], test[1]);
        assertEquals(arr1[2], test[2]);
    }

    //distance tests
    @Test
    public void testNullDistance() {
        int[] arr1 = {0, 2, 295};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = null;
        assertEquals(-1, p1.distance(p2));
    }

    @Test
    public void testSecondIsBiggerDistance() {
        int[] arr1 = {0, 2, 101};
        int[] arr2 = {1, 3, 104};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertEquals(5, p1.distance(p2));
    }

    @Test
    public void testFirstIsBiggerDistance() {
        int[] arr2 = {0, 2, 101};
        int[] arr1 = {1, 3, 104};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertEquals(5, p1.distance(p2));
    }

    //same RGB
    @Test
    public void testToString() {
        int[] arr1 = {0, 2, 101};
        Pixel p1 = new Pixel(arr1);
        assertEquals("(0, 2, 101)", p1.toString());
    }


}
