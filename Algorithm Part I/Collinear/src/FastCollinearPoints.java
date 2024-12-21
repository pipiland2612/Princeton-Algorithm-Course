import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

   private final LineSegment[] lineSegments;

   public FastCollinearPoints(Point[] points) {
       checkNull(points);
       Point[] sortedPoints = points.clone();
       Arrays.sort(sortedPoints);
       checkDuplicates(sortedPoints);

       int n = points.length;
       List<LineSegment> result = new ArrayList<>();
       for (int i = 0; i < n; i++){
            Point p = sortedPoints[i];
            Point[] pointsSortedBySlope = sortedPoints.clone();
            Arrays.sort(pointsSortedBySlope, p.slopeOrder());

            int j = 1;
            while (j < n){
                LinkedList<Point> cands = new LinkedList<>();
                double SLOPE_REF = p.slopeTo(pointsSortedBySlope[j]);
                do {
                    cands.add(pointsSortedBySlope[j++]);
                } while (j < n && p.slopeTo(pointsSortedBySlope[j]) == SLOPE_REF);

                if(cands.size() >= 3 && p.compareTo(cands.peek()) < 0){
                    Point max = cands.removeLast();
                    result.add(new LineSegment(p, max));
                }
            }
       }
       this.lineSegments = result.toArray(new LineSegment[0]);
   }

   private void checkNull(Point[] points){
       if(points == null)throw new IllegalArgumentException();
       for (Point point : points) {
           if (point == null) throw new IllegalArgumentException();
       }
   }
   private void checkDuplicates(Point[] points){
       for (int i = 1; i < points.length; i++) {
           if(points[i].compareTo(points[i-1]) == 0)throw new IllegalArgumentException();
       }
   }

   public int numberOfSegments() {
        return lineSegments.length;
   }

   public LineSegment[] segments() {
        return lineSegments.clone();
   }

   public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
   }
}