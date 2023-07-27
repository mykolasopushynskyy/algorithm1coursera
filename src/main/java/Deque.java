import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node back;
    private int size;

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validateNotNull(item);

        Node node = new Node(item);

        if (isEmpty()) {
            front = node;
            back = front;
        } else {
            node.next = front;
            front.prev = node;
            front = node;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validateNotNull(item);
        Node node = new Node(item);

        if (isEmpty()) {
            front = node;
            back = front;
        } else {
            node.prev = back;
            back.next = node;
            back = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validateElementExist();
        Node node = front;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            front = front.next;
            front.prev = null;
        }
        size--;
        return node.value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validateElementExist();
        Node node = back;
        if (size == 1) {
            front = null;
            back = null;
        } else {
            back = back.prev;
            back.next = null;
        }
        size--;
        return node.value;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        public Node(Item value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        Node next;
        Node prev;
        Item value;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = front;

        @Override
        public boolean hasNext() {
            return current != null && current.next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            return current.next.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void validateNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateElementExist() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}