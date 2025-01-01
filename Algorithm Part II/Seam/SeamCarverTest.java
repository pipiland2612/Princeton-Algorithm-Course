import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Color;

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
    public void testSeamFinding() {
        Picture picture = createTestPicture(6, 6);
        SeamCarver seamCarver = new SeamCarver(picture);

        int[] horizontalSeam = seamCarver.findHorizontalSeam();
        int[] verticalSeam = seamCarver.findVerticalSeam();

        // Validate seam structure
        Assertions.assertNotNull(horizontalSeam);
        Assertions.assertNotNull(verticalSeam);
        Assertions.assertEquals(6, horizontalSeam.length,
                                "Horizontal seam length should match picture width.");
        Assertions.assertEquals(6, verticalSeam.length,
                                "Vertical seam length should match picture height.");

        // Placeholder assertions for seam content
        Assertions.assertTrue(isSeamValid(horizontalSeam, 6), "Horizontal seam is not valid.");
        Assertions.assertTrue(isSeamValid(verticalSeam, 6), "Vertical seam is not valid.");
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
