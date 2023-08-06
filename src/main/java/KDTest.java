import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KDTest {

    private static final List<Point2D> EX_1 = new ArrayList<>() {{
        add(new Point2D(0.372, 0.497));
        add(new Point2D(0.564, 0.413));
        add(new Point2D(0.226, 0.577));
        add(new Point2D(0.144, 0.179));
        add(new Point2D(0.083, 0.51));
        add(new Point2D(0.32, 0.708));
        add(new Point2D(0.417, 0.362));
        add(new Point2D(0.862, 0.825));
        add(new Point2D(0.785, 0.725));
        add(new Point2D(0.499, 0.208));
    }};

    private static final RectHV R1 = new RectHV(0.703, 0.573, 0.819, 0.768);

    private static final List<Point2D> EX_2 = new ArrayList<>() {{
        add(new Point2D(0.0, 0.5));
        add(new Point2D(0.5, 0.25));
        add(new Point2D(0.75, 0.5));
        add(new Point2D(0.25, 0.0));
        add(new Point2D(1.0, 0.75));
        add(new Point2D(0.0, 0.0));
        add(new Point2D(0.5, 1.0));
        add(new Point2D(0.5, 0.75));
        add(new Point2D(1.0, 0.0));
        add(new Point2D(1.0, 0.5));
    }};

    private static final List<Point2D> CONTAINS_TEST = new ArrayList<>() {{
        add(new Point2D(0.25, 0.75));
        add(new Point2D(0.0, 0.0));
        add(new Point2D(0.75, 0.5));
        add(new Point2D(0.75, 1.0));
        add(new Point2D(0.75, 0.75));
        add(new Point2D(0.0, 0.75));
        add(new Point2D(0.5, 0.0));
        add(new Point2D(0.25, 0.25));
        add(new Point2D(0.75, 0.0));
        add(new Point2D(1.0, 0.75));
    }};


    private static final RectHV R2 = new RectHV(0.25, 0.25, 0.75, 1.0);

    private final static int X_SCALE = 32768;
    private final static int Y_SCALE = 32768;

    private static void drawRectangle(RectHV rectHV) {
        StdDraw.setPenColor(0, 0, 0);
        StdDraw.line(rectHV.xmin() * X_SCALE, rectHV.ymin() * Y_SCALE, rectHV.xmax() * X_SCALE, rectHV.ymin() * Y_SCALE);
        StdDraw.line(rectHV.xmax() * X_SCALE, rectHV.ymin() * Y_SCALE, rectHV.xmax() * X_SCALE, rectHV.ymax() * Y_SCALE);
        StdDraw.line(rectHV.xmax() * X_SCALE, rectHV.ymax() * Y_SCALE, rectHV.xmin() * X_SCALE, rectHV.ymax() * Y_SCALE);
        StdDraw.line(rectHV.xmin() * X_SCALE, rectHV.ymax() * Y_SCALE, rectHV.xmin() * X_SCALE, rectHV.ymin() * Y_SCALE);
    }

    private static void drawPoint(Point2D p) {
        drawPoint(p, Color.BLACK);
    }

    private static void drawPoint(Point2D p, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(p.x() * X_SCALE, p.y() * Y_SCALE, 200);
        StdDraw.setPenColor(Color.GRAY);
        StdDraw.setFont(new Font("Consolas", Font.PLAIN, 12));
        StdDraw.text(p.x() * X_SCALE, p.y() * Y_SCALE - 200, p.toString());
    }

    private static void visualizeKDRange(List<Point2D> points, RectHV rectangle) {
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, X_SCALE);
        StdDraw.setYscale(0, Y_SCALE);

        for (Point2D p : points) {
            drawPoint(p);
        }

        drawRectangle(rectangle);

        StdDraw.show();

        KdTree kdTree = new KdTree();

        for (Point2D p : points)
            kdTree.insert(p);

        Iterable<Point2D> range = kdTree.range(rectangle);
        for (Point2D p : range) {
            drawPoint(p, Color.RED);
        }
        StdDraw.show();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
//        visualizeKDRange(EX_1, R1);
        visualizeKDRange(EX_2, R2);
    }
}
