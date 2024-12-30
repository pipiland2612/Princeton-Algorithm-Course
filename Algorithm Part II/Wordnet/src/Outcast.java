import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

    public String outcast(String[] nouns) {
        String best = "";
        int maxDist = Integer.MIN_VALUE;
        for (String nounA : nouns) {
            int sum = 0;
            for (String nounB : nouns) {
                if (nounA.equals(nounB)) continue;
                sum += wordnet.distance(nounA, nounB);
            }

            if (sum > maxDist) {
                maxDist = sum;
                best = nounA;
            }
        }
        return best;
    }
}