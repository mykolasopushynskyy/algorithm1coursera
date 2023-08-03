import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final Board initial;
    private final MinPQ<SearchNode> movesPQ;
    private SearchNode last;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.initial = initial;
        this.movesPQ = new MinPQ<>(manhattanPriority());
        solve();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return last != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return last == null ? -1 : last.count;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<>();
        SearchNode move = last;
        while (move != null) {
            solution.push(move.board);
            move = move.previous;
        }
        return last == null ? null : solution;
    }

    private static Comparator<SearchNode> manhattanPriority() {
        return Comparator.comparingInt(node -> node.count + node.board.manhattan());
    }

    private static Comparator<SearchNode> hammingPriority() {
        return Comparator.comparingInt(node -> node.count + node.board.hamming());
    }

    private static class SearchNode {
        private final Board board;
        private final SearchNode previous;
        private final int count;

        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.count = previous == null ? 0 : previous.count + 1;
        }
    }

    private void solve() {
        boolean shouldSearch;
        movesPQ.insert(new SearchNode(initial, null));
        last = movesPQ.delMin();

        while (!last.board.isGoal()) {
            shouldSearch = false;
            for (Board nextBoard : last.board.neighbors()) {
                Board previousBoard = last.previous != null ? last.previous.board : null;
                if (!nextBoard.equals(previousBoard)) {
                    movesPQ.insert(new SearchNode(nextBoard, last));
                    shouldSearch = true;
                }
            }

            // dead end
            if (!shouldSearch) {
                return;
            }

            last = movesPQ.delMin();
        }
    }


    /**
     * ===================================================================================
     * Test Data and methods for debugging
     * ===================================================================================
     */
    private static final int[][] EX_1 = new int[][]{
            {0, 1, 3},
            {4, 2, 5},
            {7, 8, 6}
    };

    // test client (see below)
    public static void main(String[] args) {
        Solver s = new Solver(new Board(EX_1));
        assert s.solution() != null;
        for(Board b: s.solution()) {
            StdOut.println(b);
        }
    }

}