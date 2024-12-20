import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // Use a doubly linked list to store items
    private static class Node<Item> {
        Item item;
        Node<Item> next, prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    private Node<Item> head, tail;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> newNode = new Node<>(item);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int randomIndex = (int) (Math.random() * size);
        Node<Item> current = head;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return removeNode(current);
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int randomIndex = (int) (Math.random() * size);
        Node<Item> current = head;
        for (int i = 0; i < randomIndex; i++) {
            current = current.next;
        }
        return current.item;
    }

    // Helper method to remove a node from the linked list
    private Item removeNode(Node<Item> node) {
        if (node == head) {
            return removeFirst();
        } else if (node == tail) {
            return removeLast();
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            Item item = node.item;
            node.next = null;
            node.prev = null;
            size--;
            return item;
        }
    }

    // Remove the first node and return its item
    private Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Node<Item> temp = head;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
            temp.next = null;
        }
        size--;
        return temp.item;
    }

    // Remove the last node and return its item
    private Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Node<Item> temp = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
            temp.prev = null;
        }
        size--;
        return temp.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Node<Item>[] nodes;
        private int currentIndex;

        public RandomizedQueueIterator() {
            nodes = new Node[size];
            Node<Item> current = head;
            for (int i = 0; i < size; i++) {
                nodes[i] = current;
                current = current.next;
            }
            shuffle(nodes);
            currentIndex = 0;
        }

        private void shuffle(Node<Item>[] array) {
            for (int i = array.length - 1; i > 0; i--) {
                int randomIndex = (int) (Math.random() * (i + 1));
                Node<Item> temp = array[i];
                array[i] = array[randomIndex];
                array[randomIndex] = temp;
            }
        }

        public boolean hasNext() {
            return currentIndex < nodes.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            return nodes[currentIndex++].item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
