import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DequeTest {

    private Deque<Integer> deque;

    @BeforeEach
    public void setUp() {
        deque = new Deque<>();
    }

    @Test
    public void testIsEmptyInitially() {
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    @Test
    public void testAddFirst() {
        deque.addFirst(1);
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
        assertEquals(1, deque.removeFirst());
    }

    @Test
    public void testAddLast() {
        deque.addLast(2);
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
        assertEquals(2, deque.removeLast());
    }

    @Test
    public void testAddFirstAndLast() {
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(2, deque.size());
        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.removeLast());
    }

    @Test
    public void testRemoveFirst() {
        deque.addFirst(3);
        deque.addLast(4);
        assertEquals(3, deque.removeFirst());
        assertEquals(4, deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testRemoveLast() {
        deque.addFirst(5);
        deque.addLast(6);
        assertEquals(6, deque.removeLast());
        assertEquals(5, deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddFirstWithNull() {
        assertThrows(IllegalArgumentException.class, () -> deque.addFirst(null));
    }

    @Test
    public void testAddLastWithNull() {
        assertThrows(IllegalArgumentException.class, () -> deque.addLast(null));
    }

    @Test
    public void testRemoveFirstWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> deque.removeFirst());
    }

    @Test
    public void testRemoveLastWhenEmpty() {
        assertThrows(NoSuchElementException.class, () -> deque.removeLast());
    }

    @Test
    public void testIterator() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        int[] expected = {1, 2, 3};
        int index = 0;
        for (int item : deque) {
            assertEquals(expected[index], item);
            index++;
        }
    }

    @Test
    public void testIteratorNoSuchElement() {
        deque.addLast(1);
        Iterator<Integer> iterator = deque.iterator();
        iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testIteratorRemoveUnsupported() {
        deque.addLast(1);
        Iterator<Integer> iterator = deque.iterator();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }
}
