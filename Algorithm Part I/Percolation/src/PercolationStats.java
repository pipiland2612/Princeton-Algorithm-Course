import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    int n;
    int nSquared;
    int trials;
    double[] results;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0)throw new IllegalArgumentException("Invalid");
        this.n = n;
        this.nSquared = n * n;
        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            calculateStats(perc, i);
        }
    }

    private void calculateStats(Percolation perc, int k){
        while (!perc.percolates()){
            int i = StdRandom.uniformInt(1, n + 1);
            int j = StdRandom.uniformInt(1, n + 1);
            if(perc.isOpen(i,j))continue;
            perc.open(i,j);
        }
        results[k] = ((perc.numberOfOpenSites() * 1.0) / nSquared);
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        double mean = mean();
        double stddev = stddev();
        return (mean - ((1.96 * stddev) / Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double mean = mean();
        double stddev = stddev();
        return (mean + ((1.96 * stddev) / Math.sqrt(trials)));
    }

   // test client (see below)
   public static void main(String[] args) {
        if(args.length != 0){
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats ps = new PercolationStats(n, trials);
            StdOut.printf("mean                    = %f%n", ps.mean());
            StdOut.printf("stddev                  = %f%n", ps.stddev());
            StdOut.printf("95%% confidence interval = %f, %f%n", ps.confidenceLo(), ps.confidenceHi());
        }
    }
}