package week4;

import edu.princeton.cs.algs4.StdOut;

public class BoardTest {
    private static final int[][] EX_1 = new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
    };

    private static final int[][] EX_2 = new int[][]{
            {8, 1, 3},
            {4, 2, 0},
            {7, 6, 5}
    };

    private static final int[][] GOAL = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    // unit testing (not graded)
    public static void main(String[] args) {
        Board board1 = new Board(EX_1);
        Board board2 = new Board(EX_1);
        Board board3 = new Board(EX_2);
        Board board4 = new Board(GOAL);

        StdOut.println(board1);
        assert board1.hamming() == 5 : "Hamming must be 5";
        assert board1.manhattan() == 10 : "Manhattan must be 10";
        assert board1.equals(board2) : "Boards with same tiles must be equals";
        assert !board1.equals(board3) : "Boards with different tiles must not be equals";
        assert board4.isGoal() : "Board must be the goal board";
        assert !board1.isGoal() : "Board must not be the goal board";
        assert !board3.isGoal() : "Board must not be the goal board";
        printNeighbours(board1);
        assert board1.equals(board2) : "Boards are not equal after printing neighbours";
        assert !board1.equals(board1.twin()) : "Boards are not equal with twin";
    }

    private static void printNeighbours(Board board) {
        StdOut.println(board);
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
    }
}
