package Project_2;

import javax.swing.JComponent;//imports all necessary libraries
import java.awt.*;
import java.util.Random;

public class Enemy extends JComponent {

    static final long serialVersionUID = 1L;

    private int direction;
    
    public Enemy(int direction) {//constructor
        super();

        Random random = new Random();
        int x = random.nextInt(720);//gets two random numbers on the boards pixels
        int y = random.nextInt(720);

        int w = 0;
        for (int i = 0; i < 750; i += 30) {
            if (x <= i && x >= w) {//based off of the random number in x, determines which grid point
                x = i;//the number corresponds to
                break;
            } 
            w = i;
        }

        int z = 0;
        for (int i = 0; i < 750; i += 30) {
            if (y <= i && y >= z) {
                y = i;//based off of the random number in y, determines which grid point
                break;//the number corresponds to
            }
            z = i;
        }

        this.direction = direction;//sets global direction variable equal to the 
                                  //the passed in direction
        this.setSize(30, 30);
        this.setLocation(x, y);
        this.setVisible(true);
    }

    public void setDirection(int direction) {//setter method for enemy's direction
        this.direction = direction;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);//sets color to green
        g2d.fillRect(5, 5, 15, 15);//creates a rectangle
        if (direction == 1) {//case one for enemy facing to the left
            g2d.setColor(Color.BLUE);
            g2d.fillRect(0, 5, 6, 15);
        }
        else if (direction == 2) {//case two for enemy facing up
            g2d.setColor(Color.BLUE);
            g2d.fillRect(5, 0, 15, 6);
        }
        else if (direction == 3) {//case three for enemy facing to the right
            g2d.setColor(Color.BLUE);
            g2d.fillRect(20, 5, 6, 15);
        }
        else if (direction == 4) {//case four for enemy facing down
            g2d.setColor(Color.BLUE);
            g2d.fillRect(5, 20, 15, 6);
        }
    }
}