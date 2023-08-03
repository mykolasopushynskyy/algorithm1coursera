import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

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
        throw new RuntimeException();
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
        throw new RuntimeException();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        throw new RuntimeException();
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
     * Test Data for debugging
     * ===================================================================================
     */

    private static int[][] EX_1 = new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
    };
    private static int[][] EX_2 = new int[][]{
            {8, 1, 3},
            {4, 2, 0},
            {7, 6, 5}
    };

    // unit testing (not graded)
    public static void main(String[] args) {
        Board board1 = new Board(EX_1);
        Board board2 = new Board(EX_1);
        Board board3 = new Board(EX_2);

        StdOut.println(board1);
        assert board1.hamming() == 5 : "Hamming must be 5";
        assert board1.manhattan() == 10 : "Manhattan must be 10";
        assert board1.equals(board2) : "Boards with same tiles must be equals";
        assert !board1.equals(board3) : "Boards with different tiles must not be equals";
    }

}