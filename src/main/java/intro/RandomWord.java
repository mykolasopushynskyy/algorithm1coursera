package intro;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        String champion = "";
        int i = 0;

        while (!StdIn.isEmpty()) {
            i++;
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / (double) i)) {
                champion = word;
            }
        }

        StdOut.println(champion);
    }

}