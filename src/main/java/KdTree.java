import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        this.size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateNotNull(p);
        root = insert(root, p, true);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateNotNull(p);
        return get(root, p, true) != null;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateNotNull(rect);
        return range(root, rect, true, new ArrayList<>());
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateNotNull(p);
        return nearest(root, p, true, null);
    }

    private void validateNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;

        public Node(Point2D point) {
            this.point = point;
        }
    }

    private Node insert(Node node, Point2D point, boolean isVertical) {
        if (node == null) {
            size++;
            return new Node(point);
        }

        if (node.point.equals(point)) {
            return node;
        }

        if (isVertical) {
            if (node.point.x() < point.x()) {
                node.right = insert(node.right, point, !isVertical);
            }
            if (node.point.x() >= point.x()) {
                node.left = insert(node.left, point, !isVertical);
            }
        } else {
            if (node.point.y() < point.y()) {
                node.right = insert(node.right, point, !isVertical);
            }
            if (node.point.y() >= point.y()) {
                node.left = insert(node.left, point, !isVertical);
            }
        }

        return node;
    }

    private Node get(Node node, Point2D point, boolean isVertical) {
        if (node == null) {
            return null;
        }

        if (node.point.equals(point)) {
            return node;
        }

        if (isVertical) {
            if (node.point.x() < point.x()) {
                return get(node.right, point, !isVertical);
            }
            if (node.point.x() >= point.x()) {
                return get(node.left, point, !isVertical);
            }
        } else {
            if (node.point.y() < point.y()) {
                return get(node.right, point, !isVertical);
            }
            if (node.point.y() >= point.y()) {
                return get(node.left, point, !isVertical);
            }
        }

        return null;
    }

    private void draw(Node node) {
        if (node == null) {
            return;
        }
        node.point.draw();

        draw(node.left);
        draw(node.right);
    }

    private Iterable<Point2D> range(Node node, RectHV rectHV, boolean isVertical, ArrayList<Point2D> points) {
        if (node == null) {
            return points;
        }

        if (rectHV.contains(node.point)) {
            points.add(node.point);
        }

        if (isVertical) {
            if (node.point.x() < rectHV.xmin()) {
                range(node.right, rectHV, !isVertical, points);
            }
            if (node.point.x() > rectHV.xmax()) {
                range(node.left, rectHV, !isVertical, points);
            }
            if (node.point.x() >= rectHV.xmin() && node.point.x() <= rectHV.xmax()) {
                range(node.left, rectHV, !isVertical, points);
                range(node.right, rectHV, !isVertical, points);
            }
        } else {
            if (node.point.y() < rectHV.ymin()) {
                range(node.right, rectHV, !isVertical, points);
            }
            if (node.point.y() > rectHV.ymax()) {
                range(node.left, rectHV, !isVertical, points);
            }
            if (node.point.y() >= rectHV.ymin() && node.point.y() <= rectHV.ymax()) {
                range(node.left, rectHV, !isVertical, points);
                range(node.right, rectHV, !isVertical, points);
            }
        }
        return points;
    }

    private Point2D nearest(Node node, Point2D query, boolean isVertical, Point2D nearest) {
        if (node == null) {
            return nearest;
        }

        // root node initialization
        if (nearest == null) {
            nearest = node.point;
        }

        // champion found
        if (query.distanceSquaredTo(node.point) < query.distanceSquaredTo(nearest)) {
            nearest = node.point;
        }

        if (isVertical) {
            if (node.point.x() > query.x()) {
                nearest = nearest(node.left, query, !isVertical, nearest);

                if (nearest.distanceTo(query) > Math.abs(node.point.x() - query.x())) {
                    Point2D nearestRight = nearest(node.right, query, !isVertical, nearest);
                    nearest = nearest.distanceSquaredTo(query) > nearestRight.distanceSquaredTo(query) ? nearestRight : nearest;
                }
            } else {
                nearest = nearest(node.right, query, !isVertical, nearest);

                if (nearest.distanceTo(query) > Math.abs(node.point.x() - query.x())) {
                    Point2D nearestLeft = nearest(node.left, query, !isVertical, nearest);
                    nearest = nearestLeft.distanceSquaredTo(query) > nearest.distanceSquaredTo(query) ? nearest : nearestLeft;
                }
            }
        } else {
            if (node.point.y() > query.y()) {
                nearest = nearest(node.left, query, !isVertical, nearest);

                if (nearest.distanceTo(query) > Math.abs(node.point.y() - query.y())) {
                    Point2D nearestRight = nearest(node.right, query, !isVertical, nearest);
                    nearest = nearest.distanceSquaredTo(query) > nearestRight.distanceSquaredTo(query) ? nearestRight : nearest;
                }
            } else {
                nearest = nearest(node.right, query, !isVertical, nearest);

                if (nearest.distanceTo(query) > Math.abs(node.point.y() - query.y())) {
                    Point2D nearestLeft = nearest(node.left, query, !isVertical, nearest);
                    nearest = nearestLeft.distanceSquaredTo(query) > nearest.distanceSquaredTo(query) ? nearest : nearestLeft;
                }
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        throw new RuntimeException();
    }
}
