import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {

    @Test
    void testIsEmpty() {
        KdTree kdTree = new KdTree();
        assertTrue(kdTree.isEmpty(), "Tree should be empty initially");
        kdTree.insert(new Point2D(0.5, 0.5));
        assertFalse(kdTree.isEmpty(), "Tree should not be empty after insertion");
    }

    @Test
    void testSize() {
        KdTree kdTree = new KdTree();
        assertEquals(0, kdTree.size(), "Size should be 0 initially");
        kdTree.insert(new Point2D(0.5, 0.5));
        assertEquals(1, kdTree.size(), "Size should be 1 after one insertion");
        kdTree.insert(new Point2D(0.2, 0.8));
        assertEquals(2, kdTree.size(), "Size should be 2 after two insertions");
    }

    @Test
    void testInsertAndContains() {
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.5, 0.5);
        Point2D p2 = new Point2D(0.2, 0.8);
        Point2D p3 = new Point2D(0.9, 0.4);

        kdTree.insert(p1);
        kdTree.insert(p2);

        assertTrue(kdTree.contains(p1), "Tree should contain the inserted point p1");
        assertTrue(kdTree.contains(p2), "Tree should contain the inserted point p2");
        assertFalse(kdTree.contains(p3), "Tree should not contain a point not inserted");

        kdTree.insert(p3);
        assertTrue(kdTree.contains(p3), "Tree should now contain the inserted point p3");
    }

    @Test
    void testRange() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.2, 0.8));
        kdTree.insert(new Point2D(0.9, 0.4));

        RectHV rect = new RectHV(0.1, 0.1, 0.6, 0.6);
        Iterable<Point2D> pointsInRange = kdTree.range(rect);

        assertNotNull(pointsInRange, "Range query should not return null");

        List<Point2D> points = new ArrayList<>();
        pointsInRange.forEach(points::add);

        assertEquals(1, points.size(), "Range query should return exactly one point");
        assertTrue(points.contains(new Point2D(0.5, 0.5)), "Range query should return the correct point");
    }

    @Test
    void testNearest() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.2, 0.8));
        kdTree.insert(new Point2D(0.9, 0.4));

        Point2D query = new Point2D(0.4, 0.4);
        Point2D nearest = kdTree.nearest(query);

        assertNotNull(nearest, "Nearest neighbor query should not return null");
        assertEquals(0.5, nearest.x(), 0.1, "Nearest neighbor x-coordinate should match");
        assertEquals(0.5, nearest.y(), 0.1, "Nearest neighbor y-coordinate should match");
    }

    @Test
    void testNearestMultiplePoints() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(0.9, 0.9));
        kdTree.insert(new Point2D(0.4, 0.4));

        Point2D query = new Point2D(0.45, 0.45);
        Point2D nearest = kdTree.nearest(query);

        assertNotNull(nearest, "Nearest neighbor query should not return null");
        assertEquals(0.4, nearest.x(), 0.1, "Nearest neighbor x-coordinate should match");
        assertEquals(0.4, nearest.y(), 0.1, "Nearest neighbor y-coordinate should match");
    }

    @Test
    void testEdgeCases() {
        KdTree kdTree = new KdTree();

        // Test inserting the same point multiple times
        Point2D p = new Point2D(0.5, 0.5);
        kdTree.insert(p);
        kdTree.insert(p);
        assertEquals(1, kdTree.size(), "Tree should not count duplicate points");

        // Test range query with no points in range
        RectHV rect = new RectHV(0.6, 0.6, 1.0, 1.0);
        Iterable<Point2D> pointsInRange = kdTree.range(rect);
        assertNotNull(pointsInRange, "Range query should not return null");
        assertFalse(pointsInRange.iterator().hasNext(), "Range query should return no points");
    }

    @Test
    void testNearestEdgeCases() {
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.5, 0.5);
        Point2D p2 = new Point2D(0.2, 0.8);
        kdTree.insert(p1);
        kdTree.insert(p2);

        // Query point is at the same location as one of the points in the tree
        Point2D query = new Point2D(0.5, 0.5);
        Point2D nearest = kdTree.nearest(query);
        assertEquals(p1, nearest, "Nearest point should be the one in the tree at the same location");

        // Query point is at an extreme location
        query = new Point2D(1.0, 1.0);
        nearest = kdTree.nearest(query);
        assertNotNull(nearest, "Nearest point should be found for query point at extreme location");
    }

    @Test
    void testRangeEmptyQuery() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 0.5));
        kdTree.insert(new Point2D(0.2, 0.8));

        // Range query with no points inside the rectangle
        RectHV rect = new RectHV(1.0, 1.0, 2.0, 2.0);
        Iterable<Point2D> pointsInRange = kdTree.range(rect);
        assertNotNull(pointsInRange, "Range query should not return null");
        assertFalse(pointsInRange.iterator().hasNext(), "Range query should return no points");
    }

    @Test
    void testRangeExactMatch() {
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.5, 0.5);
        Point2D p2 = new Point2D(0.2, 0.8);
        kdTree.insert(p1);
        kdTree.insert(p2);

        // Range query with an exact match
        RectHV rect = new RectHV(0.2, 0.8, 0.3, 0.9); // Querying point (0.2, 0.8)
        Iterable<Point2D> pointsInRange = kdTree.range(rect);
        assertNotNull(pointsInRange, "Range query should not return null");

        List<Point2D> points = new ArrayList<>();
        pointsInRange.forEach(points::add);

        assertEquals(1, points.size(), "Range query should return exactly one point");
        assertTrue(points.contains(p2), "Range query should return the exact point within the rectangle");
    }

    @Test
    void testNearestWithIdenticalPoints() {
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.5, 0.5);
        Point2D p2 = new Point2D(0.5, 0.5);  // Identical point
        kdTree.insert(p1);
        kdTree.insert(p2);

        Point2D query = new Point2D(0.5, 0.5); // Query point exactly at one of the points
        Point2D nearest = kdTree.nearest(query);
        assertEquals(p1, nearest, "Nearest should return the first inserted point for identical points");
    }

    @Test
    void testRangeQueryMultiplePoints() {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.1, 0.2));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.3, 0.4));
        kdTree.insert(new Point2D(0.4, 0.5));

        // Range query that should return multiple points
        RectHV rect = new RectHV(0.0, 0.0, 0.3, 0.4);
        Iterable<Point2D> pointsInRange = kdTree.range(rect);

        assertNotNull(pointsInRange, "Range query should not return null");

        List<Point2D> points = new ArrayList<>();
        pointsInRange.forEach(points::add);

        assertEquals(3, points.size(), "Range query should return exactly three points");
        assertTrue(points.contains(new Point2D(0.1, 0.2)), "Range query should return point (0.1, 0.2)");
        assertTrue(points.contains(new Point2D(0.2, 0.3)), "Range query should return point (0.2, 0.3)");
        assertTrue(points.contains(new Point2D(0.3, 0.4)), "Range query should return point (0.3, 0.4)");
    }

    @Test
    void testRangeEmptyTree() {
        KdTree kdTree = new KdTree();
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        Iterable<Point2D> pointsInRange = kdTree.range(rect);

        assertNotNull(pointsInRange, "Range query should not return null");
        assertFalse(pointsInRange.iterator().hasNext(), "Range query on empty tree should return no points");
    }
}
