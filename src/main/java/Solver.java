import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final Board initial;
    private final MinPQ<SearchNode> movePQ;
    private final MinPQ<SearchNode> twinPQ;
    private SearchNode last;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        Comparator<SearchNode> priority = manhattanPriority();

        this.initial = initial;
        this.movePQ = new MinPQ<>(priority);
        this.twinPQ = new MinPQ<>(priority);
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
        if (last == null) {
            return null;
        }

        Stack<Board> solution = new Stack<>();
        SearchNode move = last;
        while (move != null) {
            solution.push(move.board);
            move = move.previous;
        }
        return solution;
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
        SearchNode twin;
        SearchNode next;

        movePQ.insert(new SearchNode(initial, null));
        twinPQ.insert(new SearchNode(initial.twin(), null));

        do {
            next = findNextMove(movePQ);
            twin = findNextMove(twinPQ);
        } while (!next.board.isGoal() && !twin.board.isGoal());

        this.last = next.board.isGoal() ? next : null;
    }

    private SearchNode findNextMove(MinPQ<SearchNode> moves) {
        SearchNode nextMove = moves.delMin();

        if (nextMove.board.isGoal()) {
            return nextMove;
        }

        for (Board nextBoard : nextMove.board.neighbors()) {
            Board previousBoard = nextMove.previous != null ? nextMove.previous.board : null;
            if (!nextBoard.equals(previousBoard)) {
                moves.insert(new SearchNode(nextBoard, nextMove));
            }
        }

        return nextMove;
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