import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // extra points
        queue.queue = new String[k];

        for (int i = 0; i < k; i ++ ) {
            queue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i ++ ) {
            StdOut.println(queue.dequeue());
        }
    }
}
