import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

   private final LineSegment[] lineSegments;
   public BruteCollinearPoints(Point[] points){
       checkNull(points);
       int n = points.length;
       List<LineSegment> result = new ArrayList<>();
       Point[] sortedPoint = points.clone();
       Arrays.sort(sortedPoint);
       checkDuplicates(sortedPoint);
       for (int i = 0; i < n - 3; i++) {
           for (int j = i + 1; j < n - 2; j++) {
               double slope1 = sortedPoint[i].slopeTo(sortedPoint[j]);
               for (int k = j + 1; k < n - 1; k++) {
                   double slope2 = sortedPoint[j].slopeTo(sortedPoint[k]);
                   if(slope1 != slope2)continue;
                   for (int l = k + 1; l < n; l++) {
                       double slope3 = sortedPoint[k].slopeTo(sortedPoint[l]);
                       if(slope1 != slope3)continue;
                       result.add(new LineSegment(sortedPoint[i], sortedPoint[l]));
                   }
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

   public int numberOfSegments(){
       return lineSegments.length;
   }

   public LineSegment[] segments(){
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}