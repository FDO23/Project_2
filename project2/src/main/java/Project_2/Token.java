package Project_2;

import javax.swing.JComponent;//imports all necessary libraries
import java.awt.*;
import java.util.Random;

public class Token extends JComponent {

    static final long serialVersionUID = 1L;

    private boolean immune = false;
    
    public Token(boolean immune) {//constructor
        super();

        Random random = new Random();
        int x = random.nextInt(720);//gets two random numbers on the boards pixels
        int y = random.nextInt(720);

        int w = 0;
        for (int i = 0; i < 750; i += 30) {
            if (x <= i && x >= w) {
                x = i;//based off of the random number in x, determines which grid point
                break;//the number corresponds to
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

        this.immune = immune;

        this.setSize(30, 30);//sets the size equal to that of one gridpoint
        this.setLocation(x, y);
        this.setVisible(true);
    }

    public void setImmunity(boolean immune) {//setter method to change immunity
        this.immune = immune;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (immune == false) {
            g2d.setColor(Color.RED);//sets the color to red
        }
        else if (immune == true) {
            g2d.setColor(Color.BLACK);//sets the color to black
        }
        else {
            g2d.setColor(Color.RED);//sets the color to red
        }
        g2d.fillOval(2, 2, 25, 25);//creates a circle
    }
}