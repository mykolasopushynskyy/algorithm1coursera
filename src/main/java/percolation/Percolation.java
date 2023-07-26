package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final byte CLOSED = 0b000;
    private static final byte OPEN = 0b100;
    private static final byte TOP = 0b010;
    private static final byte BOTTOM = 0b001;
    private static final byte PERCOLATE = 0b111;

    private final WeightedQuickUnionUF unionFind;
    private final byte[] values;
    private final int n;

    private int numberOfOpenSights;
    private boolean isPercolate;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.isPercolate = false;
        this.values = initializeMatrix();
        this.numberOfOpenSights = 0;
        this.unionFind = new WeightedQuickUnionUF(n * n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRC(row, col);
        if (isOpen(row, col)) {
            return;
        }

        numberOfOpenSights++;

        byte value = OPEN;
        if (row == n) {
            value = (byte) (value | BOTTOM);
        }
        if (row == 1) {
            value = (byte) (value | TOP);
        }

        int currentIndex = index(row, col);
        int oldRootIndex;
        int newRootIndex;
        values[currentIndex] = value;

        // connect left neighbor
        if (col != 1 && isOpen(row, col - 1)) {
            oldRootIndex = unionFind.find(index(row, col - 1));
            unionFind.union(currentIndex, index(row, col - 1));
            newRootIndex = unionFind.find(currentIndex);
            values[currentIndex] = (byte) (values[newRootIndex] | values[oldRootIndex] | values[currentIndex]);
        }

        // connect right neighbor
        if (col != n && isOpen(row, col + 1)) {
            oldRootIndex = unionFind.find(index(row, col + 1));
            unionFind.union(currentIndex, index(row, col + 1));
            newRootIndex = unionFind.find(currentIndex);
            values[currentIndex] = (byte) (values[newRootIndex] | values[oldRootIndex] | values[currentIndex]);
        }

        // connect top neighbor
        if (row != 1 && isOpen(row - 1, col)) {
            oldRootIndex = unionFind.find(index(row - 1, col));
            unionFind.union(currentIndex, index(row - 1, col));
            newRootIndex = unionFind.find(currentIndex);
            values[currentIndex] = (byte) (values[newRootIndex] | values[oldRootIndex] | values[currentIndex]);
        }

        // connect top neighbor
        if (row != n && isOpen(row + 1, col)) {
            oldRootIndex = unionFind.find(index(row + 1, col));
            unionFind.union(currentIndex, index(row + 1, col));
            newRootIndex = unionFind.find(currentIndex);
            values[currentIndex] = (byte) (values[newRootIndex] | values[oldRootIndex] | values[currentIndex]);
        }

        values[unionFind.find(currentIndex)] = values[currentIndex];
        isPercolate = values[currentIndex] == PERCOLATE || isPercolate;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRC(row, col);
        return (values[index(row, col)] & OPEN) == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRC(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        return (byte) (values[unionFind.find(index(row, col))] & TOP) == TOP;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSights;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolate;
    }


    private void validateRC(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException();
        }
    }

    private byte[] initializeMatrix() {
        byte[] field = new byte[n * n];
        for (int i = 0; i < n * n; i++) {
            field[i] = CLOSED;
        }
        return field;
    }

    private int index(int row, int col) {
        return (row - 1) * n + col - 1;
    }
//
//    public void print(int row, int col) {
//        for (int i = 0; i < n; i++) {
//            System.out.print("||");
//            for (int j = 0; j < n; j++) {
//                if ((byte) (values[unionFind.find(index(i + 1, j + 1))] & OPEN) == OPEN) {
//                    System.out.print("··");
//                } else {
//                    System.out.print("▒▒");
//                }
//            }
//            System.out.print("||");
//            for (int j = 0; j < n; j++) {
//                if (i + 1 == row && j + 1 == col) {
//                    System.out.print("XX");
//                } else if ((byte) (values[unionFind.find(index(i + 1, j + 1))] & (TOP | BOTTOM | OPEN)) == (TOP | BOTTOM | OPEN)) {
//                    System.out.print("██");
//                } else if ((byte) (values[unionFind.find(index(i + 1, j + 1))] & TOP) == TOP) {
//                    System.out.print("▓▓");
//                } else if ((byte) (values[unionFind.find(index(i + 1, j + 1))] & BOTTOM) == BOTTOM) {
//                    System.out.print("▒▒");
//                } else {
//                    System.out.print("░░");
//                }
//            }
//            System.out.print("||");
//            for (int j = 0; j < n; j++) {
//                if (isFull(i + 1, j + 1)) {
//                    System.out.print("▓▓");
//                } else {
//                    System.out.print("░░");
//                }
//            }
//
//            System.out.print("||");
//            System.out.println();
//        }
//        System.out.println();
//    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println((byte) (OPEN | TOP | BOTTOM) == PERCOLATE);
    }
}
