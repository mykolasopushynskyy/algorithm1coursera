public class Solver {

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        throw new RuntimeException();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        throw new RuntimeException();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        throw new RuntimeException();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        throw new RuntimeException();
    }

    // test client (see below)
    public static void main(String[] args){
        throw new RuntimeException();
    }

}