import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarverTest {

    // Helper to create a simple test picture
    private Picture createTestPicture(int width, int height) {
        Picture picture = new Picture(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                picture.set(x, y, new Color((x + y) % 256, (x * y) % 256, (x + y * 2) % 256));
            }
        }
        return picture;
    }

    // Test constructor and getters
    @Test
    public void testConstructor() {
        Picture picture = createTestPicture(5, 5);
        SeamCarver seamCarver = new SeamCarver(picture);

        Assertions.assertEquals(5, seamCarver.width());
        Assertions.assertEquals(5, seamCarver.height());
        Assertions.assertNotNull(seamCarver.picture());
        Assertions.assertNotSame(picture, seamCarver.picture(),
                                 "The picture should be deep-copied.");
    }

    // Test energy calculation for edge and non-edge pixels
    @Test
    public void testEnergyCalculation() {
        Picture picture = createTestPicture(3, 3);
        SeamCarver seamCarver = new SeamCarver(picture);

        // Test edge pixels
        Assertions.assertEquals(1000.0, seamCarver.energy(0, 0),
                                "Edge energy should always be 1000.");
        Assertions.assertEquals(1000.0, seamCarver.energy(2, 2),
                                "Edge energy should always be 1000.");

        // Test non-edge pixel
        double expectedEnergy = seamCarver.energy(1, 1);
        Assertions.assertTrue(expectedEnergy > 0,
                              "Energy for non-edge pixel should be greater than 0.");
    }

    // Test seam finding (placeholder until implemented)
    @Test
    public void testSeamFinding3x4() {
        // Create a 3x4 picture with the given pixel energies
        Picture picture = new Picture(3, 4);

        // Top row
        picture.set(0, 0, new Color(255, 0, 0));   // Dummy colors
        picture.set(1, 0, new Color(255, 0, 0));
        picture.set(2, 0, new Color(255, 0, 0));

        // Second row
        picture.set(0, 1, new Color(255, 0, 0));
        picture.set(1, 1, new Color(100, 100, 100)); // Corresponds to 228.53
        picture.set(2, 1, new Color(255, 0, 0));

        // Third row
        picture.set(0, 2, new Color(255, 0, 0));
        picture.set(1, 2, new Color(80, 80, 80));   // Corresponds to 228.09
        picture.set(2, 2, new Color(255, 0, 0));

        // Bottom row
        picture.set(0, 3, new Color(255, 0, 0));
        picture.set(1, 3, new Color(255, 0, 0));
        picture.set(2, 3, new Color(255, 0, 0));

        // Initialize SeamCarver with the picture
        SeamCarver seamCarver = new SeamCarver(picture);

        // Expected vertical seam
        int[] expectedVerticalSeam = { 0, 1, 1, 1 };
        // Expected horizontal seam
        int[] expectedHorizontalSeam = { 0, 1, 1 };

        // Calculate seams
        int[] verticalSeam = seamCarver.findVerticalSeam();
        int[] horizontalSeam = seamCarver.findHorizontalSeam();


        System.out.println("actual vertical: " + Arrays.toString(verticalSeam));
        System.out.println("actual horizontal: " + Arrays.toString(horizontalSeam));
        System.out.println("expected horizontal:" + Arrays.toString(expectedHorizontalSeam));
        // Validate vertical seam
        Assertions.assertArrayEquals(expectedVerticalSeam, verticalSeam,
                                     "Vertical seam does not match the expected seam.");

        // Validate horizontal seam
        Assertions.assertArrayEquals(expectedHorizontalSeam, horizontalSeam,
                                     "Horizontal seam does not match the expected seam.");
    }

    @Test
    public void testSeamFinding3x7() {
        // Create a 3x7 picture with the given pixel energies
        Picture picture = new Picture(3, 7);

        // Top row
        picture.set(0, 0, new Color(255, 0, 0));   // Dummy colors
        picture.set(1, 0, new Color(255, 0, 0));
        picture.set(2, 0, new Color(255, 0, 0));

        // Second row
        picture.set(0, 1, new Color(255, 0, 0));
        picture.set(1, 1, new Color(100, 100, 100)); // Corresponds to 294.32
        picture.set(2, 1, new Color(255, 0, 0));

        // Third row
        picture.set(0, 2, new Color(255, 0, 0));
        picture.set(1, 2, new Color(80, 80, 80));   // Corresponds to 236.17
        picture.set(2, 2, new Color(255, 0, 0));

        // Fourth row
        picture.set(0, 3, new Color(255, 0, 0));
        picture.set(1, 3, new Color(120, 120, 120)); // Corresponds to 325.15
        picture.set(2, 3, new Color(255, 0, 0));

        // Fifth row
        picture.set(0, 4, new Color(255, 0, 0));
        picture.set(1, 4, new Color(90, 90, 90));   // Corresponds to 251.36
        picture.set(2, 4, new Color(255, 0, 0));

        // Sixth row
        picture.set(0, 5, new Color(255, 0, 0));
        picture.set(1, 5, new Color(110, 110, 110)); // Corresponds to 279.64
        picture.set(2, 5, new Color(255, 0, 0));

        // Bottom row
        picture.set(0, 6, new Color(255, 0, 0));
        picture.set(1, 6, new Color(255, 0, 0));
        picture.set(2, 6, new Color(255, 0, 0));

        // Initialize SeamCarver with the picture
        SeamCarver seamCarver = new SeamCarver(picture);

        // Expected vertical seam
        int[] expectedVerticalSeam = { 0, 1, 1, 1, 1, 1, 1 };
        // Expected horizontal seam
        int[] expectedHorizontalSeam = { 2, 3, 2 };

        // Calculate seams
        int[] verticalSeam = seamCarver.findVerticalSeam();
        int[] horizontalSeam = seamCarver.findHorizontalSeam();


        System.out.println("actual vertical: " + Arrays.toString(verticalSeam));
        System.out.println("expected vertical: " + Arrays.toString(expectedVerticalSeam));
        System.out.println("actual horizontal: " + Arrays.toString(horizontalSeam));
        System.out.println("expected horizontal:" + Arrays.toString(expectedHorizontalSeam));
        // Validate vertical seam
        Assertions.assertArrayEquals(expectedVerticalSeam, verticalSeam,
                                     "Vertical seam does not match the expected seam.");

        // Validate horizontal seam
        Assertions.assertArrayEquals(expectedHorizontalSeam, horizontalSeam,
                                     "Horizontal seam does not match the expected seam.");
    }


    // Test seam removal and validity checks
    @Test
    public void testSeamRemoval() {
        Picture picture = createTestPicture(6, 6);
        SeamCarver seamCarver = new SeamCarver(picture);

        int[] verticalSeam = { 0, 1, 2, 1, 0, 0 };
        seamCarver.removeVerticalSeam(verticalSeam);
        Assertions.assertEquals(5, seamCarver.width(),
                                "Width should decrease after removing a vertical seam.");

        int[] horizontalSeam = { 0, 1, 1, 2, 2, 1 };
        seamCarver.removeHorizontalSeam(horizontalSeam);
        Assertions.assertEquals(5, seamCarver.height(),
                                "Height should decrease after removing a horizontal seam.");
    }

    // Test invalid seam arguments
    @Test
    public void testInvalidSeams() {
        Picture picture = createTestPicture(6, 6);
        SeamCarver seamCarver = new SeamCarver(picture);

        // Invalid seam (out of bounds)
        int[] outOfBoundsSeam = { 0, 7, 1, 2, 3, 4 };
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> seamCarver.removeVerticalSeam(outOfBoundsSeam));

        // Invalid seam (length mismatch)
        int[] shortSeam = { 0, 1, 2 };
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> seamCarver.removeVerticalSeam(shortSeam));

        // Invalid seam (disconnected elements)
        int[] disconnectedSeam = { 0, 2, 4, 6, 8, 10 };
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> seamCarver.removeHorizontalSeam(disconnectedSeam));
    }

    // Helper to validate seam
    private boolean isSeamValid(int[] seam, int length) {
        if (seam.length != length) return false;
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) return false;
        }
        return true;
    }
}
