import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> tree;

    public PointSET() {
        tree = new TreeSet<>();
    }

    public static void main(String[] args) {

    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public int size() {
        return tree.size();
    }

    public void insert(Point2D p) {
        checkNull(p);
        tree.add(p);
    }

    public boolean contains(Point2D p) {
        checkNull(p);
        return tree.contains(p);
    }

    public void draw() {
        for (Point2D p : tree) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);
        List<Point2D> results = new ArrayList<>();
        for (Point2D p : tree) {
            if (rect.contains(p)) {
                results.add(p);
            }
        }
        return results;
    }

    public Point2D nearest(Point2D p) {
        checkNull(p);
        if (isEmpty()) return null;

        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Point2D point : tree) {
            double dist = p.distanceSquaredTo(point);
            if (dist < minDist) {
                minDist = dist;
                nearest = point;
            }
        }
        return nearest;
    }


    private void checkNull(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }
}