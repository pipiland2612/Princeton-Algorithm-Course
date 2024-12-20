import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RandomizedQueue<T> implements Iterable<T> {

    private final List<T> list;

    public RandomizedQueue() {
        list = new ArrayList<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void enqueue(T item) {
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
    

    public T dequeue() {
        return list.remove(randomIndex());
    }


    public T sample() {
        return list.get(randomIndex());
    }

    private class RandomizedQueueIterator implements Iterator<T> {
        public boolean hasNext() {
            return size() > 0;
        }
        public T next() {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException();
            }
            return dequeue();
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }


    public Iterator<T> iterator() {
        return new RandomizedQueueIterator();
    }
}