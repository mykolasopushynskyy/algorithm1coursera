package week3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private final Point[] points;
    private List<LineSegment> segmentList;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);
        this.segmentList = new ArrayList<>();
        this.points = Arrays.copyOf(points, points.length);
        findLineSegments();
    }

    private void findLineSegments() {
        Arrays.sort(points);
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r]) &&
                                points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])
                        ) {
                            LineSegment lineSegment = new LineSegment(points[p], points[s]);
                            segmentList.add(lineSegment);
                        }
                    }
                }
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
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}