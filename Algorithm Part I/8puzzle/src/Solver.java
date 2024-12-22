import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private final boolean isSolvable;
    private SearchNode solutionNode;

    public Solver(Board initial) {
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(new SearchNode(initial, 0, null));
        while (true){
            SearchNode currNode = minPQ.delMin();
            Board currBoard = currNode.board;
            if(currBoard.isGoal()){
                isSolvable = true;
                this.solutionNode = currNode;
                break;
            }

            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
                isSolvable = false;
                break;
            }
            int currMove = currNode.moves;
            Board prevBoard = currMove > 0 ? currNode.prev.board : null;

            for (Board neighbor : currBoard.neighbors()){
                if(neighbor.equals(prevBoard))continue;
                minPQ.insert(new SearchNode(neighbor, currMove + 1, currNode));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solutionNode.moves : -1;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) {
            return null;
        }
        Deque<Board> solution = new LinkedList<>();
        SearchNode node = solutionNode;
        while (node != null) {
            solution.addFirst(node.board);
            node = node.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

    private static class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode o) {
            return (this.board.manhattan() + this.moves) - (o.board.manhattan() + o.moves);
        }
    }

}