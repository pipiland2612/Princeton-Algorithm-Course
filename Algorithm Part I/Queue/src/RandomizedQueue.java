import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final List<Item> list;

    public RandomizedQueue() {
        list = new ArrayList<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        list.add(item);
    }

    public int randomIndex() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return StdRandom.uniformInt(size());
    }
    

    public Item dequeue() {
        return list.remove(randomIndex());
    }


    public Item sample() {
        return list.get(randomIndex());
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        public boolean hasNext() {
            return size() > 0;
        }
        public Item next() {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException();
            }
            return dequeue();
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
}