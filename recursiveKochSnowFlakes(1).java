
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class recursiveKochSnowFlakes extends JFrame implements ActionListener {

    int level = 0;         // Initial Level of SnowFlake

    JButton button = new JButton("Press To increase Level");       // Button to increase level by a click
    panel panel = new panel(1, 0, 0);                                         // Panel to draw SnowFlake

    final double WIDTH, HEIGHT;

    public recursiveKochSnowFlakes() {

        button.addActionListener(this);                         // adding an action listener to the button to trigger an event when its clicked
        button.setBounds(200, 10, 200, 30);        // Setting Location and size of button
        button.setFocusable(false);

        setSize(700, 600);                // Setting Size of the Frame
        setMinimumSize(getSize());        //sets the minimum size of the frame
        
        WIDTH = getWidth(); //default width
        HEIGHT = getHeight(); //default height

        setLayout(new BorderLayout()); //sets border layout of the the frame
        
        addComponentListener(new ComponentAdapter() { //add component listener to detect resize event
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension changedSize = getSize();
                if (changedSize.getWidth() > WIDTH || changedSize.getHeight() > HEIGHT) {
                    remove(panel);
                    double size = (changedSize.getWidth() + changedSize.getHeight()) / (WIDTH + HEIGHT);
                    int changedWidth = 0;
                    if ((int) Math.round(changedSize.getWidth() - WIDTH) <= 0) {
                        changedWidth = 160;
                    } else if ((int) Math.round(changedSize.getWidth() - WIDTH) <= 300) {
                        changedWidth = (int) Math.round(changedSize.getWidth() - WIDTH) - 160;
                    } else {
                        changedWidth = 300;
                    }
                    int changedHeight = 0;
                    if ((int) Math.round(changedSize.getHeight() - HEIGHT) <= 0) {
                        changedHeight = 60;
                    } else if ((int) Math.round(changedSize.getHeight() - HEIGHT) <= 60) {
                        changedHeight = (int) Math.round(changedSize.getHeight() - HEIGHT) - 60;
                    } else {
                        changedHeight = 60;
                    }
                    panel = new panel(size, changedWidth, changedHeight);
                    add(panel);
                    revalidate();
                    repaint();
                }
            }

        });
        add(panel, BorderLayout.CENTER);                                   // Adding Panel to the frame
        add(button, BorderLayout.NORTH);                                  // Adding Button to the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null); //shows frame in the centre
    }

    public static void main(String[] args) {
        new recursiveKochSnowFlakes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button)) {       //Whenever button is clicked we increase the level and remove the first panel and add a new panel
            level++;
            remove(panel);
            add(panel);
            revalidate();
            repaint();
        }
    }

    class panel extends JPanel {

        int x, y;
        double size;

        public panel(double size, int x, int y) {
            this.size = size;
            this.x = x;
            this.y = y;
        }

        public void paint(Graphics g) {                // Overriding the paint method of the panel to draw koch snowflake

            drawSnow(g, level, 160, 380, 420, 380);
            drawSnow(g, level, 420, 380, 300, 120);
            drawSnow(g, level, 300, 120, 160, 380);
        }

        private void drawSnow(Graphics g, int lev, int x1, int y1, int x5, int y5) {          // Method to draw one size of snowflake by recursion
            int deltaX, deltaY, x2, y2, x3, y3, x4, y4;
            if (lev == 0) {
                if (size > 0) {
                    g.drawLine((int) Math.round(x1 * size) + x, (int) Math.round(y1 * size) - (int) Math.round(y * size),
                            (int) Math.round(x5 * size) + x, (int) Math.round(y5 * size) - (int) Math.round((y * size)));
                } else {
                    g.drawLine(x1, y1, x5, y5);
                }
            } else {
                deltaX = x5 - x1;
                deltaY = y5 - y1;
                x2 = x1 + deltaX / 3;
                y2 = y1 + deltaY / 3;
                x3 = (int) (0.5 * (x1 + x5) + Math.sqrt(3) * (y1 - y5) / 6);
                y3 = (int) (0.5 * (y1 + y5) + Math.sqrt(3) * (x5 - x1) / 6);
                x4 = x1 + 2 * deltaX / 3;
                y4 = y1 + 2 * deltaY / 3;
                drawSnow(g, lev - 1, x1, y1, x2, y2);
                drawSnow(g, lev - 1, x2, y2, x3, y3);
                drawSnow(g, lev - 1, x3, y3, x4, y4);
                drawSnow(g, lev - 1, x4, y4, x5, y5);
            }
        }
    }
}
