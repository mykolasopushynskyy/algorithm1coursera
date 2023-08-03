import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {

    private final Board initial;
    private final MinPQ<SearchNode> movesPQ;
    private List<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        this.solution = null;
        this.initial = initial;
        this.movesPQ = new MinPQ<>(manhattanPriority());
        solve();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution == null ? -1 : solution.size();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution == null ? null : new ArrayList<>(solution);
    }

    private static Comparator<SearchNode> manhattanPriority() {
        return Comparator.comparingInt(node -> node.count + node.board.manhattan());
    }

    private static Comparator<SearchNode> hammingPriority() {
        return Comparator.comparingInt(node -> node.count + node.board.hamming());
    }

    private class SearchNode {
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
        SearchNode move = movesPQ.delMin();

        while (!move.board.isGoal()) {
            shouldSearch = false;
            for (Board nextBoard : move.board.neighbors()) {
                if (move.previous != null && !nextBoard.equals(move.previous.board)) {
                    movesPQ.insert(new SearchNode(nextBoard, move));
                    shouldSearch = true;
                }
            }
            // dead end
            if (!shouldSearch) {
                return;
            }
            move = movesPQ.delMin();
        }

        // convert to boards
        List<SearchNode> solution = new ArrayList<>();
        while (move.previous != null) {
            solution.add(move);
            move = move.previous;
        }
        solution.sort(Comparator.comparing(m -> m.count));
        this.solution = solution.stream().map(m -> m.board).collect(Collectors.toList());
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