import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PercolationStatsTest {

    @Test
    public void testMean() {
        PercolationStats stats = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        double mean = stats.mean();
        assertTrue(mean > 0 && mean < 1, "The mean should be between 0 and 1.");
    }


    @Test
    public void testStddev() {
        PercolationStats stats = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        double stddev = stats.stddev();
        assertTrue(stddev >= 0, "The standard deviation should be non-negative.");
    }


    @Test
    public void testConfidenceLo() {
        PercolationStats stats = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        double lo = stats.confidenceLo();
        double mean = stats.mean();
        double hi = stats.confidenceHi();
        assertTrue(lo < mean && lo < hi, "The low endpoint of the 95% confidence interval should be less than the mean and the high endpoint.");
    }


    @Test
    public void testConfidenceHi() {
        PercolationStats stats = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        double hi = stats.confidenceHi();
        double mean = stats.mean();
        double lo = stats.confidenceLo();
        assertTrue(hi > mean && hi > lo, "The high endpoint of the 95% confidence interval should be greater than the mean and the low endpoint.");
    }


    @Test
    public void testConstructorInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> new PercolationStats(-1, 30));
        assertThrows(IllegalArgumentException.class, () -> new PercolationStats(20, -10));
        assertThrows(IllegalArgumentException.class, () -> new PercolationStats(0, 30));
    }


    @Test
    public void testMultipleRuns() {
        PercolationStats stats1 = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        PercolationStats stats2 = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        assertNotEquals(stats1.mean(), stats2.mean(), "Two different runs should produce different means.");
    }


    @Test
    public void testSmallGrid() {
        PercolationStats stats = new PercolationStats(1, 30); // 1x1 grid, 30 trials
        double mean = stats.mean();
        assertTrue(mean == 1.0, "For a 1x1 grid, the mean should be 1 since it percolates immediately.");
    }


    @Test
    public void testConfidenceIntervalDifference() {
        PercolationStats stats1 = new PercolationStats(20, 30); // 20x20 grid, 30 trials
        PercolationStats stats2 = new PercolationStats(20, 100); // 20x20 grid, 100 trials
        double range1 = stats1.confidenceHi() - stats1.confidenceLo();
        double range2 = stats2.confidenceHi() - stats2.confidenceLo();
        assertTrue(range2 < range1, "The range of the confidence interval should be smaller for more trials.");
    }


    @Test
    public void testMinimumTrialsForConfidence() {
        PercolationStats stats = new PercolationStats(20, 1); // 20x20 grid, 1 trial
        double lo = stats.confidenceLo();
        double hi = stats.confidenceHi();
        assertEquals(lo, hi, "When the number of trials is 1, the confidence interval endpoints should be the same.");
    }
}
