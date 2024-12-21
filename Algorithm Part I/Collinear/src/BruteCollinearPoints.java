import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

   List<LineSegment> result;
   public BruteCollinearPoints(Point[] points){
       if(points == null)throw new IllegalArgumentException();
       for (Point point : points) {
           if (point == null) throw new IllegalArgumentException();
       }
       int n = points.length;
       result = new ArrayList<>();
       Arrays.sort(points);
       for (int i = 1; i < n; i++) {
           if(points[i].compareTo(points[i-1]) == 0)throw new IllegalArgumentException();
       }
       for (int i = 0; i < n - 3; i++) {
           for (int j = i + 1; j < n - 2; j++) {
               double slope1 = points[i].slopeTo(points[j]);
               for (int k = j + 1; k < n - 1; k++) {
                   double slope2 = points[j].slopeTo(points[k]);
                   if(slope1 != slope2)continue;
                   for (int l = k + 1; l < n; l++) {
                       double slope3 = points[k].slopeTo(points[l]);
                       if(slope1 != slope3)continue;
                       result.add(new LineSegment(points[i], points[l]));
                   }
               }
           }
       }
   }
   public int numberOfSegments(){
       return result.size();
   }
   public LineSegment[] segments(){
       return result.toArray(new LineSegment[0]);
   }
}