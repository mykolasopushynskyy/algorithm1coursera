package week4;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {

    private SearchNode last;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        solve(initial);
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

    private void solve(Board initial) {
        MinPQ<SearchNode> movePQ = new MinPQ<>(manhattanPriority());
        MinPQ<SearchNode> twinPQ = new MinPQ<>(manhattanPriority());

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

    private static Comparator<SearchNode> manhattanPriority() {
        return Comparator.comparingInt(node -> node.count + node.manhattan);
    }

    private static class SearchNode {
        private final Board board;
        private final SearchNode previous;
        private final int count;
        private final int manhattan;

        public SearchNode(Board board, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.count = previous == null ? 0 : previous.count + 1;
            // reduces manhattan calls when comparing search nodes (i.e. caching has to be done in week4.Solver class)
            // same has to be done for hamming if needed
            this.manhattan = board.manhattan();
        }
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
}