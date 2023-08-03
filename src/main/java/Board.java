import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

public class Board {
    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = copyOf(tiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(" ");
                stringBuilder.append(tiles[i][j]);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skip empty tile
                if (tiles[i][j] == 0) {
                    continue;
                }

                int expected = i * n + j + 1;
                if (tiles[i][j] != expected) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skip empty tile
                if (tiles[i][j] == 0) {
                    continue;
                }

                int expectedCol = (tiles[i][j] - 1) % n;
                int expectedRow = (tiles[i][j] - 1 - expectedCol) / n;

                manhattan += Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skip empty tile
                if (tiles[i][j] == 0) {
                    continue;
                }

                int expected = i * n + j + 1;
                if (tiles[i][j] != expected) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        if (n != board.n) return false;

        for (int i = 0; i < n; i++) {
            if (!Arrays.equals(tiles[i], board.tiles[i])) {
                return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    // swap tiles on original tiles to prevent double copy
                    if (i > 0) {
                        result.add(new Board(swap(tiles, i, j, i - 1, j)));
                        swap(tiles, i, j, i - 1, j); // revert to normal
                    }
                    if (i < n - 1) {
                        result.add(new Board(swap(tiles, i, j, i + 1, j)));
                        swap(tiles, i, j, i + 1, j); // revert to normal
                    }
                    if (j > 0) {
                        result.add(new Board(swap(tiles, i, j, i, j - 1)));
                        swap(tiles, i, j, i, j - 1); // revert to normal
                    }
                    if (j < n - 1) {
                        result.add(new Board(swap(tiles, i, j, i, j + 1)));
                        swap(tiles, i, j, i, j + 1); // revert to normal
                    }
                    return result;
                }
            }
        }

        return result;
    }

    private int[][] swap(int[][] copyOf, int i, int j, int i1, int j1) {
        int tmp = copyOf[i][j];
        copyOf[i][j] = copyOf[i1][j1];
        copyOf[i1][j1] = tmp;
        return copyOf;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board[] neighbors = ((List<Board>) neighbors()).toArray(new Board[0]);
        StdRandom.shuffle(neighbors);
        return neighbors[0];
    }

    private int[][] copyOf(int[][] tiles) {
        final int[][] copy = new int[n][];
        for (int i = 0; i < n; i++) {
            copy[i] = new int[n];
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    /**
     * ===================================================================================
     * Test Data and methods for debugging
     * ===================================================================================
     */

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
    }

    private static void printNeighbours(Board board) {
        StdOut.println(board);
        for (Board b : board.neighbors()) {
            StdOut.println(b);
        }
    }
}