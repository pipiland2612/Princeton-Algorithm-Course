import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // O: closed, 1: opened
    private final int[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int n;
    private final int VIRTUAL_TOPNODE, VIRTUAL_BOTTOMNODE;
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
        VIRTUAL_TOPNODE = n * n;
        VIRTUAL_BOTTOMNODE = n * n + 1;
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
        openHelper(row - 1, col, currIndex);
        // Open to the left
        openHelper(row, col - 1, currIndex);
        // Open to the bottom
        openHelper(row + 1, col, currIndex);
        // Open to the right
        openHelper(row, col + 1, currIndex);

        // Connect to the virtual nodes
        if (row == 0) uf.union(currIndex, VIRTUAL_TOPNODE); // Connect to top virtual node
        if (row == n - 1) uf.union(currIndex, VIRTUAL_BOTTOMNODE); // Connect to bottom virtual node
    }

    private void openHelper(int row, int col, int currIndex) {
        if (row < n && row >= 0 && col < n && col >= 0 && grid[row][col] == 1) {
            int index = row * n + col;
            uf.union(currIndex, index);
        }
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
        return uf.find(currIndex) == uf.find(VIRTUAL_TOPNODE);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(VIRTUAL_BOTTOMNODE) == uf.find(VIRTUAL_TOPNODE);
    }

    // Validates if row and col are within the correct range
    private void validateIndices(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row and column indices must be between 1 and " + n);
        }
    }
}
