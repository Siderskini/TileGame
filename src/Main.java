/**
 * Created by Siderskini1 on 10/8/17.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Main extends JFrame {
    public static final int CANVAS_WIDTH  = 640;
    public static final int CANVAS_HEIGHT = 480;

    // Declare an instance of the drawing canvas,
    // which is an inner class called DrawCanvas extending javax.swing.JPanel.
    private Canvas canvas;
    private int t;
    private long startTime = System.currentTimeMillis();

    // Constructor to set up the GUI components and event handlers
    public Main(Board board, Player one, Player two, int time) {
        t = time;
        canvas = new Canvas(board, one, two);    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);
        // or "setContentPane(canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("......");  // "super" JFrame sets the title
        setResizable(false);
        setVisible(true);    // "super" JFrame show
    }

    private class Canvas extends JPanel implements KeyListener{
        private Board board;
        private Player p1, p2;
        private int border = 1;
        private char c;
        private char c1 = ' ';
        private char c2 = ' ';
        /*
        private Timer timer = new Timer(40, new MyTimer());

        class MyTimer implements KeyListener
        {
            int xoff, yoff;
            private Graphics g;
            public MyTimer(Graphics graphics) {
                super();
                g = graphics;
                xoff = 2;
                yoff = 1;
            }

            public void actionPerformed(ActionEvent e)
            {
                //Loop stuff
                repaint();
                g.setColor(Color.WHITE);
                drawBoard(g, board, xoff, yoff);
                drawPlayers(g, p1, p2, xoff, yoff);
                //TimeUnit.MILLISECONDS.sleep(60);
                repaint();
                p1.move((int) (Math.random() * 13 % 4));
                if (p2 != null) {
                    p2.move((int) (Math.random() * 17 % 4));
                }
                //if (System.currentTimeMillis() - start > 3000) {
                //    over = true;
                //}
                board.printBoard();
            }
        }
        */

        Canvas(Board b, Player one, Player two) {
            super();
            board = b;
            p1 = one;
            p2 = two;
            addKeyListener(this);
        }
        // Override paintComponent to perform your own painting
        public void paintComponent(Graphics g) {
            if ((System.currentTimeMillis() - (t * 1000)) > startTime) {
                return;
            }
            super.paintComponent(g);     // paint parent's background
            setBackground(Color.WHITE);  // set background color for this JPanel
            int xoff = 2, yoff = 1;

            drawFrame(g, board, xoff, yoff); //Draw the grid

            board.getTile(0, 0).update(1);      //Update tile colors
            board.getTile(0, 0).update(1);
            board.getTile(board.getCols() - 1, board.getRows() - 1).update(4);
            board.getTile(board.getCols() - 1, board.getRows() - 1).update(4);

            drawBoard(g, board, xoff, yoff);    //Draw the tiles
            drawScore(g, board, xoff, yoff);    //Draw the score
            drawPlayers(g, p1, p2, xoff, yoff); //Draw the players
            p1.move(getDirection(c1));                    //Move the players
            if (p2 != null) p2.move(getDirection(c2));

            //Print the board for testing purposes
            board.printBoard();

            repaint(); //Get ready to do it again

            //Dispose of graphics
            g.dispose();
        }

        public int getDirection(char c) {
            switch (c) {
                case 'w':
                    return 0;
                case 'd':
                    return 1;
                case 's':
                    return 2;
                case 'a':
                    return 3;
                case 'i':
                    return 0;
                case 'l':
                    return 1;
                case 'k':
                    return 2;
                case 'j':
                    return 3;
                default:
                    return 4;
            }
        }

        public void addNotify() {
            super.addNotify();
            requestFocus();
        }

        public void keyPressed(KeyEvent e) {
            c = e.getKeyChar();
            if (c == 'w' || c == 'a' || c == 's' || c == 'd') {
                c1 = c;
            } else {
                c2 = c;
            }
            repaint();
        }

        public void keyReleased(KeyEvent e) { }

        public void keyTyped(KeyEvent e) { }

        private void drawFrame(Graphics g, Board b, int xoff, int yoff) {
            g.setColor(Color.BLACK);    // set the drawing color
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                    g.drawRect(50 * (i + xoff), 50 * (j + 1), 50, 50);
                }
            }
        }

        private void drawBoard(Graphics g, Board b, int xoff, int yoff) {
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getCols(); j++) {
                    Tile temp = board.getTile(i, j);
                    switch(temp.getC1()) {
                        case 0:
                            drawCheckBox1(g, i, j, xoff, yoff, Color.WHITE);
                            break;
                        case 1:
                            drawCheckBox1(g, i, j, xoff, yoff, Color.BLUE);
                            break;
                        case 2:
                            drawCheckBox1(g, i, j, xoff, yoff, Color.GREEN);
                            break;
                        case 3:
                            drawCheckBox1(g, i, j, xoff, yoff, Color.YELLOW);
                            break;
                        case 4:
                            drawCheckBox1(g, i, j, xoff, yoff, Color.RED);
                            break;
                    }
                    switch(temp.getC2()) {
                        case 0:
                            drawCheckBox2(g, i, j, xoff, yoff, Color.WHITE);
                            break;
                        case 1:
                            drawCheckBox2(g, i, j, xoff, yoff, Color.BLUE);
                            break;
                        case 2:
                            drawCheckBox2(g, i, j, xoff, yoff, Color.GREEN);
                            break;
                        case 3:
                            drawCheckBox2(g, i, j, xoff, yoff, Color.YELLOW);
                            break;
                        case 4:
                            drawCheckBox2(g, i, j, xoff, yoff, Color.RED);
                            break;
                    }
                }
            }
        }

        private void drawCheckBox1(Graphics g, int i, int j, int xoff, int yoff, Color c) {
            g.setColor(c);
            g.fillRect(50 * (i + xoff) + border, 50 * (j + yoff) + border, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 24, 50 * (j + yoff) + border, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 12, 50 * (j + yoff) + border + 12, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 36, 50 * (j + yoff) + border + 12, 12, 12);
            g.fillRect(50 * (i + xoff) + border, 50 * (j + yoff) + border + 24, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 24, 50 * (j + yoff) + border + 24, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 12, 50 * (j + yoff) + border + 36, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 36, 50 * (j + yoff) + border + 36, 12, 12);
        }

        private void drawCheckBox2(Graphics g, int i, int j, int xoff, int yoff, Color c) {
            g.setColor(c);
            g.fillRect(50 * (i + xoff) + border + 12, 50 * (j + yoff) + border, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 36, 50 * (j + yoff) + border, 12, 12);
            g.fillRect(50 * (i + xoff) + border, 50 * (j + yoff) + border + 12, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 24, 50 * (j + yoff) + border + 12, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 12, 50 * (j + yoff) + border + 24, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 36, 50 * (j + yoff) + border + 24, 12, 12);
            g.fillRect(50 * (i + xoff) + border, 50 * (j + yoff) + border + 36, 12, 12);
            g.fillRect(50 * (i + xoff) + border + 24, 50 * (j + yoff) + border + 36, 12, 12);
        }

        private void drawPlayers(Graphics g, Player p1, Player p2, int xoff, int yoff) {
            int border2 = 12;
            g.setColor(Color.BLUE);
            g.fillRect(50 * (p1.getX() + xoff) + border, 50 * (p1.getY() + yoff) + border, 50 - 2 * border,
                    50 -
                    2 * border);
            g.clearRect(50 * (p1.getX() + xoff) + border2, 50 * (p1.getY() + yoff) + border2, 2 * border2,
                            2 * border2);
            g.drawString("P1", 50 * (p1.getX() + xoff) + border2 + 4, 50 * (p1.getY() + yoff) + border2 + 18);
            if (p2 != null) {
                g.setColor(Color.RED);
                g.fillRect(50 * (p2.getX() + xoff) + border, 50 * (p2.getY() + yoff) + border, 50 - 2 *
                                border,50 - 2 * border);
                g.clearRect(50 * (p2.getX() + xoff) + border2, 50 * (p2.getY() + yoff) + border2, 2 * border2,
                        2 * border2);
                g.drawString("P2", 50 * (p2.getX() + xoff) + border2 + 4, 50 * (p2.getY() + yoff) + border2 + 18);
            }
        }

        private void drawScore(Graphics g, Board b, int xoff, int yoff) {
            g.setColor(Color.BLACK);
            int[] counts = b.count(2);
            String str = counts[1] + "    " + counts[4];
            g.drawString(str, xoff + 240, yoff + 24);
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("What size board do you want to create?? (int between 3 and 8 inclusive)");
        int size = s.nextInt();
        Board b = new Board(size, size);
        System.out.println("How many players?? (1 or 2)");
        int players = s.nextInt();
        System.out.println("How many seconds do you want to play for??");
        int time = s.nextInt();
        Player p1 = new Player(b, 1);
        Player p2;
        if (players >= 2) {
            p2 = new Player(b, 4);
        } else {
            p2 = null;
        }
        Main m = new Main(b, p1, p2, time);
    }
}
