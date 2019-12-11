package Project_2;

import javax.swing.*;//imports necessary libraries
import java.awt.*;

public class Board extends JPanel {

    static final long serialVersionUID = 1L;
    
    public Board() {//constructor
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(new Color(197, 2, 81, 1));//sets background of board
        this.setLayout(null);
        this.setSize(800, 800);//sets the size of the board
        this.isVisible();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.BLACK);//sets color to black
        
        int grid;
        for (int i = 0; i < 25; i++) {
            grid = i * 30;
            g2d.drawLine(grid, 0, grid, 750);//draws black lines on the board to create a grid
            g2d.drawLine(0, grid, 750, grid);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension dim = new Dimension(750, 750);//sets the preferred size to 750 by 750
        return dim;
    }
}