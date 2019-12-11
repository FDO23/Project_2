package Project_2;

import javax.swing.JComponent;//imports all necessary libraries
import java.awt.*;
import java.util.Random;

public class Immunity extends JComponent {

    static final long serialVersionUID = 1L;
    
    public Immunity() {//constructor
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

        this.setSize(30, 30);//sets the size equal to that of one gridpoint
        this.setLocation(x, y);
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);//sets the color to gray
        g2d.fillOval(2, 2, 5, 15);//creates an oval
    }
}