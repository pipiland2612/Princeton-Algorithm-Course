import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final SAP sap;
    private final Map<String, Set<Integer>> nounToIds;
    private final Map<Integer, String> idToSynset;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("Input files cannot be null");
        nounToIds = new HashMap<>();
        idToSynset = new HashMap<>();

        parseSynsets(synsets);
        Digraph graph = parseHypernyms(hypernyms);

        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle() || !rootedDAG(graph)) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(graph);
    }

    public static void main(String[] args) {
        // Unit testing
    }

    private void parseSynsets(String synsets) {
        if (synsets == null) throw new IllegalArgumentException();
        // Read file and populate nounToIds and idToSynset maps
        In file = new In(synsets);
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            String synset = line[1];
            idToSynset.put(id, synset);

            String[] nouns = synset.split(" ");
            for (String noun : nouns) {
                nounToIds.computeIfAbsent(noun, k -> new HashSet<>()).add(id);
            }
        }
    }

    private Digraph parseHypernyms(String hypernyms) {
        if (hypernyms == null) throw new IllegalArgumentException();
        Digraph graph = new Digraph(idToSynset.size());

        In file = new In(hypernyms);
        while (file.hasNextLine()) {
            String[] line = file.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int hypernymId = Integer.parseInt(line[i]);
                graph.addEdge(id, hypernymId);
            }
        }
        return graph;
    }

    private boolean rootedDAG(Digraph g) {
        int roots = 0;
        for (int i = 0; i < g.V(); i++) {
            if (!g.adj(i).iterator().hasNext()) {
                roots++;
                if (roots > 1) {
                    return false;
                }
            }
        }

        return roots == 1;
    }

    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null || word.isEmpty()) return false;
        return nounToIds.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        Set<Integer> idsA = nounToIds.get(nounA);
        Set<Integer> idsB = nounToIds.get(nounB);

        return sap.length(idsA, idsB);
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        Set<Integer> idsA = nounToIds.get(nounA);
        Set<Integer> idsB = nounToIds.get(nounB);
        int ancestor = sap.ancestor(idsA, idsB);
        return idToSynset.get(ancestor);
    }
}
