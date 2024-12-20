import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // Use doubly linked list
    private static class Node<Item> {
        Item item;
        Node<Item> next, prev;
        public Node(Item item) {
            this.item = item;
        }
    }
    private Node<Item> head, tail;
    private int size;
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
    public void addFirst(Item item){
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> newNode = new Node<>(item);
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
    public void addLast(Item item){
        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        Node<Item> newNode = new Node<>(item);
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
    public Item removeFirst(){
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node<Item> temp = head;
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
    public Item removeLast(){
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
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

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>{
        private Node<Item> curr = head;
        public boolean hasNext(){
            return curr != null;
        }
        public Item next(){
            if(!hasNext())throw new NoSuchElementException();
            Item item = curr.item;
            curr = curr.next;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }

}