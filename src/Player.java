/**
 * Created by Siderskini1 on 10/8/17.
 */
public class Player {
    private int color;
    private double speed, x, y;
    private Board board;

    public Player(Board b, int c) {
        board = b;
        color = c;
        speed = 0.1;
        switch(c) {
            case 1:
                x = 0.5;
                y = 0.5;
                break;
            case 2:
                x = b.getCols() - 0.5;
                y = 0.5;
                break;
            case 3:
                x = 0.5;
                y = b.getRows() - 0.5;
                break;
            case 4:
                x = b.getCols() - 0.5;
                y = b.getRows() - 0.5;
                break;
            default:
                System.out.println("Not a valid color!!");
        }
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public void move(int dir) {
        switch (dir) {
            case 0:
                y -= speed;
                break;
            case 1:
                x += speed;
                break;
            case 2:
                y += speed;
                break;
            case 3:
                x -= speed;
                break;
            case 4:
                break;
            default:
                System.out.println("Where u goin m8??");
        }
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x > board.getCols() - 1) {
            x = board.getCols() - 1;
        }
        if (y > board.getRows() - 1) {
            y = board.getRows() - 1;
        }
        board.getTile(((int) x), ((int) y)).update(color);
    }
}
