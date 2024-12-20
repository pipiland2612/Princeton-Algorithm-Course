import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {
    // Use doubly linked list
    private static class Node<T> {
        T item;
        Node<T> next, prev;
        public Node(T item) {
            this.item = item;
        }
    }
    private Node<T> head, tail;
    int size;
    // construct an empty deque
    public Deque(){
        head = new Node<>(null);
        tail = new Node<>(null);
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(T item){
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<T> newNode = new Node<>(item);
        if(size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(T item){
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<T> newNode = new Node<>(item);
        if(size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }

    // remove and return the item from the front
    public T removeFirst(){
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node<T> temp = head;
        if(size == 1) {
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

    // remove and return the item from the back
    public T removeLast(){
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node<T> temp = tail;
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

    // return an iterator over items in order from front to back
    public Iterator<T> iterator(){
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<T>{
        private Node<T> curr = head;
        public boolean hasNext(){
            return curr != null;
        }
        public T next(){
            if(!hasNext())throw new NoSuchElementException();
            T item = curr.item;
            curr = curr.next;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
    }

}