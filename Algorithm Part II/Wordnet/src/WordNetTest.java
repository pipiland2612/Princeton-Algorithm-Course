import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WordNetTest {
    private WordNet wordNet;

    @BeforeEach
    void setUp() {
        // Initialize WordNet with test files. Adjust paths if necessary.
        wordNet = new WordNet("/Users/batman/Desktop/Princeton Algorithm/Algorithm Part II/Wordnet/synsets.txt", "/Users/batman/Desktop/Princeton Algorithm/Algorithm Part II/Wordnet/hypernyms.txt");
    }

    @Test
    void testConstructor() {
        assertNotNull(wordNet, "WordNet should be initialized successfully");
    }

    @Test
    void testNouns() {
        Iterable<String> nouns = wordNet.nouns();
        Set<String> nounSet = new HashSet<>();
        nouns.forEach(nounSet::add);

        // Check that certain expected nouns are present
        assertTrue(nounSet.contains("dog"), "'dog' should be a noun");
        assertTrue(nounSet.size() > 0, "Noun set should not be empty");
    }

    @Test
    void testDistance() {
        // Test valid distance
        int distance = wordNet.distance("hood", "1530s");
        assertTrue(distance >= 0, "Distance should be non-negative");

        distance = wordNet.distance("hood", "9/11");
        assertTrue(distance >= 0, "Distance should be non-negative");

        // Test invalid nouns
        assertThrows(IllegalArgumentException.class, () -> {
            wordNet.distance("hood", "nonexistent_word");
        }, "Distance should throw IllegalArgumentException for invalid nouns");
    }

    @Test
    void testSap() {
        // Test valid shortest ancestral path
        String sap = wordNet.sap("hood", "1530s");
        assertNotNull(sap, "Shortest ancestral path should not be null");
        assertEquals("hood", sap, "SAP should return the correct common ancestor");

        // Test invalid nouns
        assertThrows(IllegalArgumentException.class, () -> {
            wordNet.sap("hood", "nonexistent_word");
        }, "SAP should throw IllegalArgumentException for invalid nouns");
    }

    @Test
    void testEdgeCases() {
        // Test null arguments for methods
        assertThrows(IllegalArgumentException.class, () -> {
            wordNet.distance(null, "1530s");
        }, "Distance should throw IllegalArgumentException for null arguments");

        assertThrows(IllegalArgumentException.class, () -> {
            wordNet.sap(null, "1530s");
        }, "SAP should throw IllegalArgumentException for null arguments");

        assertThrows(IllegalArgumentException.class, () -> {
            new WordNet(null, "hypernyms.txt");
        }, "Constructor should throw IllegalArgumentException for null synsets file");

        assertThrows(IllegalArgumentException.class, () -> {
            new WordNet("synsets.txt", null);
        }, "Constructor should throw IllegalArgumentException for null hypernyms file");

        // Test an invalid WordNet (e.g., one without a root)
        assertThrows(IllegalArgumentException.class, () -> {
            new WordNet("invalid_synsets.txt", "invalid_hypernyms.txt");
        }, "Constructor should throw IllegalArgumentException for invalid WordNet structure");
    }

    @Test
    void testPerformance() {
        // Performance test: Ensure that large input files are handled properly
        long startTime = System.nanoTime();
        WordNet largeWordNet = new WordNet("large_synsets.txt", "large_hypernyms.txt");
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        // Assert that the construction of large input files takes less than 2 seconds
        assertTrue(duration < 2_000_000_000, "WordNet constructor should handle large files in less than 2 seconds");
    }
}
