import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public static void main(String[] args) {
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Point2D p) {
        checkNull(p);
        if (root == null) {
            this.root = new Node(p);
            size++;
            return;
        }
        rInsert(this.root, p, 0);
    }

    private Node rInsert(Node curr, Point2D p, int currDepth) {
        if (curr == null) {
            size++;
            return new Node(p);
        }

        int currDim = currDepth % 2;
        double newNode = targetAtDim(p, currDim);
        double currNode = targetAtDim(curr.p, currDim);

        if (curr.p.equals(p)) {
            return curr;
        }

        if (newNode < currNode) {
            curr.left = rInsert(curr.left, p, currDepth + 1);
        } else {
            curr.right = rInsert(curr.right, p, currDepth + 1);
        }
        return curr;
    }

    public boolean contains(Point2D p) {
        checkNull(p);
        Node curr = root;
        int currDim = 0;
        while (curr != null) {
            double newNode = targetAtDim(p, currDim);
            double currNode = targetAtDim(curr.p, currDim);
            if (curr.p.equals(p)) {
                return true;
            }

            if (newNode < currNode) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }

            currDim++;
        }
        return false;
    }

    public void draw() {
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);
        return rangeHelper(rect, root, 0);
    }

    private List<Point2D> rangeHelper(RectHV rect, Node curr, int currDepth) {
        List<Point2D> result = new ArrayList<>();
        if (curr == null) return result;
        if (rect.contains(curr.p)) {
            result.add(curr.p);
        }

        int dim = currDepth % 2;
        if (dim == 0) {
            if (rect.xmin() <= curr.p.x()) {
                result.addAll(rangeHelper(rect, curr.left, currDepth + 1));
            }
            if (rect.xmax() >= curr.p.x()) {
                result.addAll(rangeHelper(rect, curr.right, currDepth + 1));
            }
        } else {
            if (rect.ymin() <= curr.p.y()) {
                result.addAll(rangeHelper(rect, curr.left, currDepth + 1));
            }
            if (rect.ymax() >= curr.p.y()) {
                result.addAll(rangeHelper(rect, curr.right, currDepth + 1));
            }
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        checkNull(p);
        if (isEmpty()) return null;
        return nearestHelper(p, root, 0, root.p);
    }

    private Point2D nearestHelper(Point2D target, Node curr, int currDepth, Point2D best) {
        if (curr == null) return best;

        int currDim = currDepth % 2;
        if (target.distanceSquaredTo(curr.p) < target.distanceSquaredTo(best)) {
            best = curr.p;
        }

        Node next = targetAtDim(target, currDim) < targetAtDim(curr.p, currDim) ? curr.left : curr.right;
        Node other = next == curr.left ? curr.right : curr.left;

        best = nearestHelper(target, next, currDepth + 1, best);
        if (target.distanceSquaredTo(best) > Math.pow(targetAtDim(target, currDim) - targetAtDim(curr.p, currDim), 2)) {
            best = nearestHelper(target, other, currDepth + 1, best);
        }

        return best;
    }

    private void checkNull(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }

    private double targetAtDim(Point2D p, int dim) {
        return dim == 0 ? p.x() : p.y();
    }

    private static class Node {
        Point2D p;
        Node left, right;

        Node(Point2D p) {
            this.p = p;
        }
    }
}

