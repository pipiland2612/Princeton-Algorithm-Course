import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        int[][] tiles = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        board = new Board(tiles);
    }

    @Test
    void testConstructor() {
        int[][] tiles = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board board = new Board(tiles);
        assertNotNull(board, "Board constructor should create a non-null object.");

        int[][] tiles2 = {
            {1, 2},
            {3, 0}
        };
        Board board2 = new Board(tiles2);
        assertNotNull(board2, "Board constructor should handle smaller boards correctly.");
    }

    @Test
    void testToString() {
        String expected = "3\n 8  1  3 \n 4  0  2 \n 7  6  5 \n";
        assertEquals(expected, board.toString(), "toString() method did not return the expected string.");

        int[][] tiles = {
            {1, 2},
            {3, 0}
        };
        Board board2 = new Board(tiles);
        String expected2 = "2\n 1  2 \n 3  0 \n";
        assertEquals(expected2, board2.toString(), "toString() should work for smaller boards.");
    }

    @Test
    void testHamming() {
        assertEquals(5, board.hamming(), "Hamming distance calculation is incorrect.");

        int[][] tiles = {
            {1, 2},
            {3, 0}
        };
        Board board2 = new Board(tiles);
        assertEquals(0, board2.hamming(), "Hamming distance should be zero for a solved board.");
    }

    @Test
    void testManhattan() {
        assertEquals(10, board.manhattan(), "Manhattan distance calculation is incorrect.");

        int[][] tiles = {
            {1, 2},
            {3, 0}
        };
        Board board2 = new Board(tiles);
        assertEquals(0, board2.manhattan(), "Manhattan distance should be zero for a solved board.");
    }

    @Test
    void testEquals() {
        int[][] tiles1 = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        int[][] tiles2 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board board1 = new Board(tiles1);
        Board board2 = new Board(tiles2);

        assertTrue(board.equals(board1), "equals() should return true for boards with the same configuration.");
        assertFalse(board.equals(board2), "equals() should return false for boards with different configurations.");

        int[][] tiles3 = {
            {1, 2},
            {3, 0}
        };
        Board board3 = new Board(tiles3);
        assertFalse(board.equals(board3), "equals() should return false for boards of different sizes.");
    }

    @Test
    void testIsGoal() {
        int[][] tiles = {
            {1, 2},
            {3, 0}
        };
        Board board2 = new Board(tiles);
        assertTrue(board2.isGoal(), "");
    }

    @Test
    void testNeighbors() {
        Iterable<Board> neighbors = board.neighbors();
        List<String> neighborStrings = new ArrayList<>();
        for (Board neighbor : neighbors) {
            neighborStrings.add(neighbor.toString());
        }

        List<String> expectedNeighbors = Arrays.asList(
            "3\n 8  0  3 \n 4  1  2 \n 7  6  5 \n",
            "3\n 8  1  3 \n 0  4  2 \n 7  6  5 \n",
            "3\n 8  1  3 \n 4  6  2 \n 7  0  5 \n",
            "3\n 8  1  3 \n 4  2  0 \n 7  6  5 \n"
        );

        for (String expected : expectedNeighbors) {
            assertTrue(neighborStrings.contains(expected), "neighbors() is missing a valid neighbor.");
        }

        assertEquals(4, neighborStrings.size(), "neighbors() returned an incorrect number of neighbors.");

        int[][] tiles = {
            {1, 2},
            {3, 0}
        };
        Board board2 = new Board(tiles);
        Iterable<Board> neighbors2 = board2.neighbors();
        List<String> neighborStrings2 = new ArrayList<>();
        for (Board neighbor : neighbors2) {
            neighborStrings2.add(neighbor.toString());
        }

        List<String> expectedNeighbors2 = Arrays.asList(
            "2\n 1  0 \n 3  2 \n",
            "2\n 1  2 \n 0  3 \n"
        );

        for (String expected : expectedNeighbors2) {
            assertTrue(neighborStrings2.contains(expected), "neighbors() is missing a valid neighbor for smaller board.");
        }

        assertEquals(2, neighborStrings2.size(), "neighbors() returned an incorrect number of neighbors for smaller board.");
    }

    @Test
    void testPerformance() {
        int n = 128;
        int[][] tiles = new int[n][n];
        int value = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = value++;
            }
        }
        Board largeBoard = new Board(tiles);

        long startTime = System.currentTimeMillis();
        largeBoard.hamming();
        largeBoard.manhattan();
        largeBoard.neighbors();
        long endTime = System.currentTimeMillis();

        assertTrue(endTime - startTime < 1000, "Methods should execute in under 1 second for an n-by-n board with n=128.");

        n = 64;
        tiles = new int[n][n];
        value = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = value++;
            }
        }
        Board mediumBoard = new Board(tiles);

        startTime = System.currentTimeMillis();
        mediumBoard.hamming();
        mediumBoard.manhattan();
        mediumBoard.neighbors();
        endTime = System.currentTimeMillis();

        assertTrue(endTime - startTime < 500, "Methods should execute in under 500 ms for an n-by-n board with n=64.");
    }
}
