import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // O: closed, 1: opened
    private final int[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private int openSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size n must be greater than 0.");
        }
        this.grid = new int[n][n];
        this.n = n;
        this.openSite = 0;
        // Two additional top and bottom virtual nodes
        this.uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);

        // Convert to 0-indexed for internal array usage
        row--;
        col--;

        // Open the site if it's not already open
        if (grid[row][col] == 1) return;

        grid[row][col] = 1;
        openSite++;

        int currIndex = row * n + col;
        // Open to the top
        if (row - 1 >= 0 && grid[row - 1][col] == 1) {
            int top = (row - 1) * n + col;
            uf.union(currIndex, top);
        }
        // Open to the left
        if (col - 1 >= 0 && grid[row][col - 1] == 1) {
            int left = row * n + (col - 1);
            uf.union(currIndex, left);
        }
        // Open to the bottom
        if (row + 1 < n && grid[row + 1][col] == 1) {
            int bottom = (row + 1) * n + col;
            uf.union(currIndex, bottom);
        }
        // Open to the right
        if (col + 1 < n && grid[row][col + 1] == 1) {
            int right = row * n + (col + 1);
            uf.union(currIndex, right);
        }

        // Connect to the virtual nodes
        if (row == 0) uf.union(currIndex, n * n); // Connect to top virtual node
        if (row == n - 1) uf.union(currIndex, (n * n) + 1); // Connect to bottom virtual node
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);

        // Convert to 0-indexed for internal array usage
        row--;
        col--;

        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);

        // Convert to 0-indexed for internal array usage
        row--;
        col--;

        if (grid[row][col] == 0) return false;

        int currIndex = row * n + col;
        int topIndex = n * n; // Virtual top node index
        return uf.find(currIndex) == uf.find(topIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        int topIndex = n * n;
        int bottomIndex = n * n + 1;
        return uf.find(bottomIndex) == uf.find(topIndex);
    }

    // Validates if row and col are within the correct range
    private void validateIndices(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row and column indices must be between 1 and " + n);
        }
    }
}
