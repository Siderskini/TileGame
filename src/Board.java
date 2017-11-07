/**
 * Created by Siderskini1 on 10/8/17.
 */
public class Board {
    private int rows, cols;
    private Tile[][] board;

    public Board(int r, int c) {
        if (r < 3 || c < 3) {
            System.out.println("Not enough tiles!!");
        }
        rows = r;
        cols = c;
        board = new Tile[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Tile getTile(int x, int y) {
        return board[x][y];
    }

    public int[] count(int num_players) {
        int[] total = new int[5];
        for (int i = 0; i < num_players; i++) {
            total[i] = 0;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                total[getTile(i, j).getC1()]++;
                total[getTile(i, j).getC2()]++;
            }
        }
        return total;
    }

    public void printBoard() {
        Tile t;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t = getTile(j, i);
                System.out.print("  " + t.getC1() + "/" + t.getC2() + "  ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}