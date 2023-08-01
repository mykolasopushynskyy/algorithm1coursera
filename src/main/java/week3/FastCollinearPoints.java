package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final Point[] points;
    private final List<LineSegment> segmentList;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        validatePoints(points);
        this.points = Arrays.copyOf(points, points.length);
        this.segmentList = new ArrayList<>();

        findLineSegments();
    }

    private void findLineSegments() {
        Arrays.sort(this.points);
        Point[] pointsAux = Arrays.copyOf(this.points, this.points.length);

        for (int i = 0; i < points.length; i++) {
            // sort points by natural order to avoid duplicate line segment if point[i] is not the end of collinear
            Arrays.sort(pointsAux);

            // sort by slope order
            Arrays.sort(pointsAux, points[i].slopeOrder());

            for (int lo = 1, hi = 1; hi <= pointsAux.length; hi++) {
                // skip equally sloped points
                if (hi != pointsAux.length && points[i].slopeTo(pointsAux[lo]) == points[i].slopeTo(pointsAux[hi]) ) {
                    continue;
                }

                // points[i].compareTo(pointsAux[lo]) < 0 checks if point[i] is the beginning of collinear to avoid duplicates
                if (hi - lo >= 3 && points[i].compareTo(pointsAux[lo]) < 0) {
                    segmentList.add(new LineSegment(points[i], pointsAux[hi - 1]));
                }
                lo = hi;
            }
        }
    }

    public int numberOfSegments() {
        return segmentList.size();
    }

    public LineSegment[] segments() {
        return segmentList.toArray(new LineSegment[0]);
    }

    // validate points
    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < i; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
