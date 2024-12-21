import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    private final int x;
    private final int y;

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b){
            Point tmp = new Point(x, y);
            double slopeA = tmp.slopeTo(a);
            double slopeB = tmp.slopeTo(b);
            return Double.compare(slopeA, slopeB);
        }
    }

    public Point(int x, int y){
        assert x >= 0 && x <= 32767;
        assert y >= 0 && y <= 32767;
        this.x = x;
        this.y = y;
    }

    public void draw(){
        StdDraw.point(this.x, this.y);
    }

    public void drawTo(Point that){
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that){
        if(this.y < that.y)return -1;
        if(this.y == that.y && this.x < that.x)return -1;
        if(this.y == that.y && this.x == that.x)return 0;
        return 1;
    }
    public double slopeTo(Point that){
        if(this.x == that.x && this.y == that.y)return Double.NEGATIVE_INFINITY;
        if(this.y == that.y)return 0.0;
        if(this.x == that.x)return Double.POSITIVE_INFINITY;
        return (that.y - this.y) * 1.0 / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder(){
        return SLOPE_ORDER;
    }

    public static void main(String[] args) {

    }
}