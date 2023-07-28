import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    Item[] queue;
    int front;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.front = 0;
        this.queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return front == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return front;
    }

    // add the item
    public void enqueue(Item item) {
        validateNotNull(item);
        if (front == queue.length) {
            resize();
        }
        queue[front] = item;
        front++;
    }

    // remove and return a random item
    public Item dequeue() {
        validateElementsExist();

        int dequeIndex = gerRandomIndex();
        Item item = queue[dequeIndex];

        for (int i = dequeIndex; i < front - 1; i++) {
            queue[i] = queue[i + 1];
        }
        queue[front - 1] = null;
        front--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        validateElementsExist();
        return queue[gerRandomIndex()];
    }

    private int gerRandomIndex() {
        return StdRandom.uniformInt(front);
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        RandomizedQueue<Integer> randomIdx;

        public RandomizedQueueIterator() {
            randomIdx = new RandomizedQueue<>();
            for (int i = 0; i < front; i++) {
                randomIdx.enqueue(i);
            }
        }

        @Override
        public boolean hasNext() {
            return randomIdx.size() > 0;
        }

        @Override
        public Item next() {
            return queue[randomIdx.dequeue()];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private void resize() {
        int newSize = queue.length * 2;
        Item[] newQueue = (Item[]) new Object[newSize];

        for (int i = 0; i < front; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    private void validateNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateElementsExist() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private static <T> void printCollection(Iterator<T> iterator, boolean isEmpty, int size) {
        StdOut.print("isEmpty=");
        StdOut.print(isEmpty);
        StdOut.print(", size=");
        StdOut.print(size);

        StdOut.print(" [");
        while (iterator.hasNext()) {
            StdOut.print(iterator.next());
            if (iterator.hasNext()) {
                StdOut.print(",");
            }
        }
        StdOut.print("]");

        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();

        int size = 10;

        printCollection(randomizedQueue.iterator(), randomizedQueue.isEmpty(), randomizedQueue.size());
        for (int i = 0; i < size; i++) {
            randomizedQueue.enqueue(i);
            printCollection(randomizedQueue.iterator(), randomizedQueue.isEmpty(), randomizedQueue.size());
        }

        for (int i = 0; i < size; i++) {
            randomizedQueue.dequeue();
            printCollection(randomizedQueue.iterator(), randomizedQueue.isEmpty(), randomizedQueue.size());
        }
    }
}