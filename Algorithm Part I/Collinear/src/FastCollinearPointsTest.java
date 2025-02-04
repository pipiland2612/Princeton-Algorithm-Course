import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FastCollinearPointsTest {

    @Test
    public void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(null));
    }

    @Test
    public void testNullPointInArray() {
        Point[] points = { new Point(0, 0), null, new Point(1, 1) };
        assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(points));
    }

    @Test
    public void testDuplicatePoints() {
        Point[] points = { new Point(0, 0), new Point(0, 0), new Point(1, 1) };
        assertThrows(IllegalArgumentException.class, () -> new FastCollinearPoints(points));
    }

    @Test
    public void testNoCollinearPoints() {
        Point[] points = {
            new Point(0, 0), new Point(1, 2),
            new Point(2, 4), new Point(3, 1)
        };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assertEquals(0, collinear.numberOfSegments());
    }

    @Test
    public void testSingleCollinearSegment() {
        Point[] points = {
            new Point(0, 0), new Point(1, 1),
            new Point(2, 2), new Point(3, 3)
        };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assertEquals(1, collinear.numberOfSegments());
        LineSegment[] segments = collinear.segments();
        assertEquals(new LineSegment(new Point(0, 0), new Point(3, 3)), segments[0]);
    }

    @Test
    public void testMultipleCollinearSegments() {
        Point[] points = {
            new Point(0, 0), new Point(1, 1),
            new Point(2, 2), new Point(3, 3),
            new Point(0, 1), new Point(1, 2),
            new Point(2, 3), new Point(3, 4)
        };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assertEquals(2, collinear.numberOfSegments());
    }

    @Test
    public void testFewerThanFourPoints() {
        Point[] points = { new Point(0, 0), new Point(1, 1), new Point(2, 2) };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assertEquals(0, collinear.numberOfSegments());
    }

    @Test
    public void testMixedCollinearAndNonCollinear() {
        Point[] points = {
            new Point(0, 0), new Point(1, 1),
            new Point(2, 2), new Point(3, 3),
            new Point(0, 2), new Point(2, 3),
            new Point(3, 1), new Point(1, 0)
        };
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        assertEquals(1, collinear.numberOfSegments());
    }
}
