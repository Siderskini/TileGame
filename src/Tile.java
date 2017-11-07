/**
 * Created by Siderskini1 on 10/8/17.
 */
public class Tile {
    private int c1, c2;

    public Tile() {
        c1 = 0;
        c2 = 0;
    }

    public void update(int player) {
        c2 = c1;
        c1 = player;
    }

    public int getC1() {
        return c1;
    }

    public int getC2() {
        return c2;
    }
}