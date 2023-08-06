package week4;

import edu.princeton.cs.algs4.StdOut;

public class SolverTest {

    private static final int[][] EX_1 = new int[][]{
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
    };

    private static final int[][] UNSOLVABLE = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
    };

    private static final int[][] EX_2 = new int[][]{
            {0, 1},
            {3, 2}
    };

    private static final int[][] EX_3 = new int[][]{
            {6, 3, 8},
            {5, 4, 1},
            {7, 2, 0}
    };

    // test client (see below)
    public static void main(String[] args) {
        Solver unsolvable = new Solver(new Board(UNSOLVABLE));
        assert unsolvable.solution() == null;
        assert !unsolvable.isSolvable();

        long now = System.currentTimeMillis();
        System.out.println();
        Solver s = new Solver(new Board(EX_3));
        System.out.println(System.currentTimeMillis() - now);
        for (Board b : s.solution()) {
            StdOut.println(b);
        }
    }
}
