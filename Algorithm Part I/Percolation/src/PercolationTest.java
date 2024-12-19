import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PercolationTest {

    // Test for constructor with invalid grid size
    @Test
    public void testConstructorInvalidGridSize() {
        assertThrows(IllegalArgumentException.class, () -> new Percolation(0), "Grid size must be greater than 0.");
        assertThrows(IllegalArgumentException.class, () -> new Percolation(-1), "Grid size must be greater than 0.");
    }

    // Test for open with invalid row and column indices
    @Test
    public void testOpenInvalidIndices() {
        Percolation p = new Percolation(5);
        assertThrows(IllegalArgumentException.class, () -> p.open(0, 3), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.open(3, 0), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.open(6, 3), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.open(3, 6), "Row and column indices must be between 1 and 5.");
    }

    // Test for isOpen with invalid row and column indices
    @Test
    public void testIsOpenInvalidIndices() {
        Percolation p = new Percolation(5);
        assertThrows(IllegalArgumentException.class, () -> p.isOpen(0, 3), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.isOpen(3, 0), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.isOpen(6, 3), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.isOpen(3, 6), "Row and column indices must be between 1 and 5.");
    }

    // Test for isFull with invalid row and column indices
    @Test
    public void testIsFullInvalidIndices() {
        Percolation p = new Percolation(5);
        assertThrows(IllegalArgumentException.class, () -> p.isFull(0, 3), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.isFull(3, 0), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.isFull(6, 3), "Row and column indices must be between 1 and 5.");
        assertThrows(IllegalArgumentException.class, () -> p.isFull(3, 6), "Row and column indices must be between 1 and 5.");
    }

    // Test for valid open site functionality
    @Test
    public void testOpenSite() {
        Percolation p = new Percolation(5);
        p.open(1, 1);
        assertTrue(p.isOpen(1, 1), "Site should be open.");
    }

    // Test for percolates method when grid is fully blocked
    @Test
    public void testPercolatesBlocked() {
        Percolation p = new Percolation(5);
        assertFalse(p.percolates(), "Grid should not percolate initially.");
    }

    // Test for percolates method when system percolates
    @Test
    public void testPercolatesOpened() {
        Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        assertTrue(p.percolates(), "Grid should percolate after opening a path.");
    }
}
