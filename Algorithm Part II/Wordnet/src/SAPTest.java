import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SAPTest {
    private Digraph digraph;
    private SAP sap;

    @BeforeEach
    void setUp() {
        // Example setup of Digraph G. You should replace this with your actual Digraph creation logic.
        // For example, constructing a simple digraph with vertices and edges
        digraph = new Digraph(6); // 6 vertices, for example
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(4, 5);

        // Create the SAP object using the digraph
        sap = new SAP(digraph);
    }

    @Test
    void testLength() {
        // Normal case: vertices with a common ancestor
        int length = sap.length(0, 3);
        assertEquals(3, length, "Length of shortest ancestral path between 0 and 3 should be 3.");

        // No path between vertices
        length = sap.length(3, 4);
        assertEquals(-1, length, "Length should be -1 as there is no path between 3 and 4.");

        // Another valid path
        length = sap.length(1, 2);
        assertEquals(1, length, "Length of shortest ancestral path between 1 and 2 should be 1.");
    }

    @Test
    void testAncestor() {
        // Normal case: vertices with a common ancestor
        int ancestor = sap.ancestor(0, 3);
        assertEquals(1, ancestor, "The common ancestor of 0 and 3 should be 1.");

        // No common ancestor
        ancestor = sap.ancestor(3, 4);
        assertEquals(-1, ancestor, "There should be no common ancestor between 3 and 4.");

        // Another valid ancestor
        ancestor = sap.ancestor(1, 2);
        assertEquals(2, ancestor, "The common ancestor of 1 and 2 should be 2.");
    }

    @Test
    void testLengthIterable() {
        // Test with two sets of vertices
        List<Integer> set1 = Arrays.asList(0, 1);
        List<Integer> set2 = Arrays.asList(2, 3);

        int length = sap.length(set1, set2);
        assertEquals(3, length, "Length of shortest ancestral path between sets {0, 1} and {2, 3} should be 3.");
    }

    @Test
    void testAncestorIterable() {
        // Test with two sets of vertices
        List<Integer> set1 = Arrays.asList(0, 1);
        List<Integer> set2 = Arrays.asList(2, 3);

        int ancestor = sap.ancestor(set1, set2);
        assertEquals(1, ancestor, "The common ancestor between sets {0, 1} and {2, 3} should be 1.");
    }

    @Test
    void testInvalidArguments() {
        // Test null arguments
        assertThrows(IllegalArgumentException.class, () -> new SAP(null), "Should throw exception for null digraph.");

        assertThrows(IllegalArgumentException.class, () -> sap.length(-1, 3), "Should throw exception for invalid vertex -1.");
        assertThrows(IllegalArgumentException.class, () -> sap.length(0, 6), "Should throw exception for invalid vertex 6.");

        assertThrows(IllegalArgumentException.class, () -> sap.ancestor(null, Arrays.asList(1, 2)), "Should throw exception for null iterable argument.");
    }

    @Test
    void testEdgeCases() {
        // Test for a vertex with no connections (isolated)
        int length = sap.length(4, 5);
        assertEquals(-1, length, "Length should be -1 for disconnected vertices 4 and 5.");

        int ancestor = sap.ancestor(4, 5);
        assertEquals(-1, ancestor, "There should be no common ancestor for disconnected vertices 4 and 5.");
    }
}
