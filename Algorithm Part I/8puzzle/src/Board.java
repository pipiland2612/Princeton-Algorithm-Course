import java.util.ArrayList;
import java.util.List;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] board;
    private final int[][] BOARD_REF;
    private final int SIZE;

    public Board(int[][] tiles) {
        this.SIZE = tiles.length;
        this.board = copyBoard(tiles);

        this.BOARD_REF = new int[SIZE * SIZE][2];
        int row = 0, col = 0;
        for (int i = 1; i < SIZE * SIZE; i++) {
            BOARD_REF[i] = new int[]{row, col++};
            if(col == SIZE){
                col = 0;
                row++;
            }
        }
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(SIZE).append("\n");
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sb.append(String.format("%2d ", board[row][col]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return SIZE;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int tile = board[i][j];
                if(tile == 0)continue;
                int[] goalCoords = BOARD_REF[tile];
                if(i != goalCoords[0] || j != goalCoords[1])count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int tile = board[i][j];
                if(tile == 0)continue;
                int[] goalCoords = BOARD_REF[tile];
                sum += Math.abs(goalCoords[0] - i) + Math.abs(goalCoords[1] - j);
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if(!(y instanceof Board that))return false;
        if(this.SIZE != that.SIZE)return false;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(this.board[i][j] != that.board[i][j])return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> boardList = new ArrayList<>();
        int blankRow = -1, blankCol = -1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(board[i][j] == 0){
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
            if(blankRow != -1)break;
        }

        int[][] directions = {
            {-1, 0}, // Up
            {1, 0}, // Down
            {0, -1}, // Left
            {0, 1} // Right
        };

        for (int[] dir : directions) {
            int newRow = blankRow + dir[0], newCol = blankCol + dir[1];
            if(newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE){
                int[][] newBoard = copyBoard(this.board);
                newBoard[blankRow][blankCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;
                boardList.add(new Board(newBoard));
            }
        }
        return boardList;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = copyBoard(this.board);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(twin[i][j] != 0 && twin[i][j + 1] != 0){
                    // swapping
                    int tmp = twin[i][j];
                    twin[i][j] = twin[i][j + 1];
                    twin[i][j + 1] = tmp;
                    return new Board(twin);
                }
            }
        }
        return this;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}