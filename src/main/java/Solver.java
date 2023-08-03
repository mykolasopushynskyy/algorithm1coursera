import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private final Board initial;
    private final List<Board> solution;
    private final MinPQ<SearchNode> nodeMinPQ;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.initial = initial;
        this.solution = new ArrayList<>();
        this.nodeMinPQ = new MinPQ<>(manhattan());
        solve();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution.size() == 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size() == 0 ? -1 : solution.size();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution.size() == 0 ? null : new ArrayList<>(solution);
    }

    private static Comparator<SearchNode> manhattan() {
        return Comparator.comparingInt(node -> node.count + node.board.manhattan());
    }

    private static Comparator<SearchNode> hamming() {
        return Comparator.comparingInt(node -> node.count + node.board.hamming());
    }

    private class SearchNode {
        private final Board board;
        private final SearchNode previous;
        private int count;

        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.count = previous == null ? 0 : previous.count + 1;
        }
    }

    private void solve() {
        nodeMinPQ.insert(new SearchNode(initial, null));

    }


    /**
     * ===================================================================================
     * Test Data and methods for debugging
     * ===================================================================================
     */
    // test client (see below)
    public static void main(String[] args) {

    }

}