import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;
import java.util.stream.Collectors;

public class PointSET {
    private final TreeSet<Point2D> rbBST;

    // construct an empty set of points
    public PointSET() {
        this.rbBST = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return rbBST.isEmpty();
    }

    // number of points in the set
    public int size() {
        return rbBST.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateNotNull(p);
        rbBST.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateNotNull(p);
        return rbBST.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        rbBST.forEach(Point2D::draw);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateNotNull(rect);
        return rbBST.stream()
                .filter(rect::contains)
                .collect(Collectors.toList());
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateNotNull(p);
        Point2D nearest = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D point : rbBST) {
            if (distance > point.distanceSquaredTo(p)) {
                distance = point.distanceSquaredTo(p);
                nearest = point;
            }
        }
        return nearest;
    }

    private void validateNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * ===================================================================================
     * Test Data and methods for debugging
     * ===================================================================================
     */

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        throw new RuntimeException();
    }
}