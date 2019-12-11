package Project_2;

import javax.swing.JComponent;//imports all necessary java libraries
import java.awt.*;
import java.util.Random;

public class Coin extends JComponent {

    static final long serialVersionUID = 1L;
    
    public Coin() {//constructor
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
                break;//the number coorresponds to
            }
            z = i;
        }

        this.setSize(30, 30);//sets the size equal to the size of one gridpoint
        this.setLocation(x, y);//sets the location to the randomized gridpoint
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);//sets color of coin to pink
        g2d.fillRect(10, 10, 10, 10);//sets size and shape of coin
    }
}