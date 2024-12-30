import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SAP {
    private final Digraph graph;
    private final HashMap<String, SAPProcessor> cache;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.graph = new Digraph(G);
        this.cache = new HashMap<>();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private void validateIndex(int i) {
        if (i < 0 || i >= graph.V()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateIndex(Iterable<Integer> v) {
        for (int vertex : v) {
            validateIndex(vertex);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateIndex(v);
        validateIndex(w);
        return cachedResult(v, w).distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateIndex(v);
        validateIndex(w);
        return cachedResult(v, w).ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateIndex(v);
        validateIndex(w);
        return cachedResult(v, w).distance;
    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateIndex(v);
        validateIndex(w);
        return cachedResult(v, w).ancestor;
    }

    private SAPProcessor cachedResult(int v, int w) {
        String key = v + "_" + w;
        if (cache.containsKey(key)) {
            SAPProcessor p = cache.get(key);
            // Cache the result to get optimized time for repetitive call
            cache.remove(key);
            return p;
        }
        SAPProcessor p = new SAPProcessor(v, w);
        cache.put(key, p);
        return p;
    }

    private SAPProcessor cachedResult(Iterable<Integer> v, Iterable<Integer> w) {
        String key = v.toString() + "_" + w.toString();
        if (cache.containsKey(key)) {
            SAPProcessor p = cache.get(key);
            // Cache the result to get optimized time for repetitive call
            cache.remove(key);
            return p;
        }

        SAPProcessor p = new SAPProcessor(v, w);
        cache.put(key, p);
        return p;
    }

    private class SAPProcessor {
        int ancestor;
        int distance;

        public SAPProcessor(int v, int w) {
            BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(graph, v);
            BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);

            process(a, b);
        }

        public SAPProcessor(Iterable<Integer> v, Iterable<Integer> w) {
            BreadthFirstDirectedPaths a = new BreadthFirstDirectedPaths(graph, v);
            BreadthFirstDirectedPaths b = new BreadthFirstDirectedPaths(graph, w);

            process(a, b);
        }

        private void process(BreadthFirstDirectedPaths a, BreadthFirstDirectedPaths b) {
            List<Integer> ancestors = new ArrayList<>();
            for (int i = 0; i < graph.V(); i++) {
                if (a.hasPathTo(i) && b.hasPathTo(i)) {
                    ancestors.add(i);
                }
            }

            int shortestAncestor = -1, minDistance = Integer.MAX_VALUE;
            for (int anc : ancestors) {
                int dist = a.distTo(anc) + b.distTo(anc);
                if (dist < minDistance) {
                    minDistance = dist;
                    shortestAncestor = anc;
                }
            }
            distance = minDistance == Integer.MAX_VALUE ? -1 : minDistance;
            ancestor = shortestAncestor;
        }
    }
}