import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Client {

    public static final Point[] EX_1 = new Point[]{
            new Point(19000, 10000),
            new Point(18000, 10000),
            new Point(32000, 10000),
            new Point(21000, 10000),
            new Point(1234, 5678),
            new Point(14000, 10000),
            new Point(14000, 15000),
            new Point(6000, 7000)
    };

    public static final Point[] EX_2 = new Point[]{
            new Point(10000, 0),
            new Point(0, 10000),
            new Point(3000, 7000),
            new Point(7000, 3000),
            new Point(20000, 21000),
            new Point(3000, 4000)
    };

    public static final Point[] EX_3 = new Point[]{
            new Point(12870, 10826),
            new Point(6036, 10826),
            new Point(9683, 16305),
            new Point(17248, 10826),
            new Point(5426, 16305),
            new Point(4550, 11680),
            new Point(9080, 11680),
            new Point(20188, 10826),
            new Point(10647, 18284),
            new Point(15155, 11680),
            new Point(1351, 16305),
            new Point(18980, 11084),
            new Point(18137, 11084),
            new Point(14346, 18284),
            new Point(13520, 11084),
            new Point(6339, 18284),
            new Point(7376, 18284),
            new Point(2326, 11084),
            new Point(19648, 16305),
            new Point(10415, 11680)
    };

    public static final Point[] EX_4 = new Point[]{
            new Point(9347 * 2, 9402 * 2),
            new Point(13750 * 2, 7080 * 2),
            new Point(9161 * 2, 9252 * 2),
            new Point(3175 * 2, 5987 * 2),
            new Point(8444 * 2, 9128 * 2),
            new Point(5175 * 2, 7337 * 2),
            new Point(7519 * 2, 7481 * 2),
            new Point(7348 * 2, 7481 * 2),
            new Point(12886 * 2, 4758 * 2),
            new Point(7859 * 2, 8202 * 2),
            new Point(5095 * 2, 7283 * 2),
            new Point(11452 * 2, 7481 * 2),
            new Point(8657 * 2, 9254 * 2),
            new Point(9225 * 2, 9590 * 2),
            new Point(4455 * 2, 6851 * 2),
            new Point(8696 * 2, 8877 * 2),
            new Point(12566 * 2, 3898 * 2),
            new Point(12704 * 2, 11648 * 2),
            new Point(13686 * 2, 6908 * 2),
            new Point(8089 * 2, 7481 * 2)
    };

    private static void executeFastCollinear(Point[] points) {
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
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private static void executeBruteCollinear(Point[] points) {
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
        LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public static void main(String[] args) {
        executeFastCollinear(EX_4);
//        executeBruteCollinear(EX_4);
    }


}
