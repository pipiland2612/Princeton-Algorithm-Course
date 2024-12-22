import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    // Original test case 1
    @Test
    void testSolverWithSolvablePuzzle() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertTrue(solver.isSolvable(), "The board should be solvable.");
        assertEquals(0, solver.moves(), "The number of moves for the solved board should be 0.");

        List<Board> expectedSolution = new ArrayList<>();
        expectedSolution.add(initial);
        Iterable<Board> solution = solver.solution();
        assertNotNull(solution, "Solution should not be null for a solvable board.");

        int stepCount = 0;
        for (Board board : solution) {
            assertTrue(expectedSolution.contains(board), "The solution should contain valid boards.");
            stepCount++;
        }
        assertEquals(expectedSolution.size(), stepCount, "The solution size should match the expected solution.");
    }

    // Original test case 2
    @Test
    void testSolverWithUnsolvablePuzzle() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertFalse(solver.isSolvable(), "The board should be unsolvable.");
        assertEquals(-1, solver.moves(), "The number of moves for an unsolvable board should be -1.");
        assertNull(solver.solution(), "Solution should be null for an unsolvable board.");
    }

    // Original test case 2
    @Test
    void testSolverWithUnsolvablePuzzle2() {
        int[][] tiles = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 15, 14, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertFalse(solver.isSolvable(), "The board should be unsolvable.");
        assertEquals(-1, solver.moves(), "The number of moves for an unsolvable board should be -1.");
        assertNull(solver.solution(), "Solution should be null for an unsolvable board.");
    }

    // Duplicated test case 2 (modified slightly for variety)
    @Test
    void testSolverWithAnotherUnsolvablePuzzle() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {8, 7, 0}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertFalse(solver.isSolvable(), "The board should be unsolvable.");
        assertEquals(-1, solver.moves(), "The number of moves for an unsolvable board should be -1.");
        assertNull(solver.solution(), "Solution should be null for an unsolvable board.");
    }

    // Original test case 3
    @Test
    void testMoves() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 0, 8}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertTrue(solver.isSolvable(), "The board should be solvable.");
        assertEquals(1, solver.moves(), "The number of moves to solve should be 1.");
    }

    // Duplicated test case 3 (modified slightly for variety)
    @Test
    void testMovesWithDifferentPuzzle() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 5},
            {7, 8, 6}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertTrue(solver.isSolvable(), "The board should be solvable.");
        assertEquals(2, solver.moves(), "The number of moves to solve should be 2.");
    }

    // Original test case 4
    @Test
    void testSolutionSequence() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 6},
            {0, 7, 8}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertTrue(solver.isSolvable(), "The board should be solvable.");
        Iterable<Board> solution = solver.solution();
        assertNotNull(solution, "Solution should not be null for a solvable board.");

        int stepCount = 0;
        for (Board board : solution) {
            assertNotNull(board, "Each board in the solution sequence should be non-null.");
            stepCount++;
        }
        assertTrue(stepCount > 0, "The solution sequence should have at least one step.");
    }

    // Duplicated test case 4 (modified slightly for variety)
    @Test
    void testSolutionSequenceVariant() {
        int[][] tiles = {
            {1, 2, 3},
            {4, 5, 0},
            {7, 8, 6}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertTrue(solver.isSolvable(), "The board should be solvable.");
        Iterable<Board> solution = solver.solution();
        assertNotNull(solution, "Solution should not be null for a solvable board.");

        int stepCount = 0;
        for (Board board : solution) {
            assertNotNull(board, "Each board in the solution sequence should be non-null.");
            stepCount++;
        }
        assertTrue(stepCount > 0, "The solution sequence should have at least one step.");
    }

    // Original test case 5
    @Test
    void testSolverWithLargeBoard() {
        int[][] tiles = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 0, 15}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        assertTrue(solver.isSolvable(), "The large board should be solvable.");
        assertTrue(solver.moves() > 0, "The moves should be greater than 0 for the large solvable board.");
    }

}
