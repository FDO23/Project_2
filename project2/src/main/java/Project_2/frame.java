package Project_2;

import java.awt.BorderLayout;//imports all the necessary libraries 
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;
import javax.swing.*;

import org.omg.CORBA.CurrentHelper;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.PasswordAuthentication;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import sun.audio.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class frame extends JFrame {

    static final long serialVersionUID = 1L;

    private Board board;// creates the basic components of the board
    private Token token;
    private Enemy enemy;
    private Coin coin;
    private Immunity immune;
    private Portal portal;

    private ArrayList<Coin> list;

    private JButton start;// creates operational buttons for the game
    private JButton quit;
    private JButton pause;
    private JButton maze;
    private JButton reset;

    private JButton up;// creates directional buttons to control the player
    private JButton down;
    private JButton left;
    private JButton right;

    private JTextArea textArea;// creates textAreas to display time elasped and score
    private JTextArea score;

    private Timer time1;// creates timers
    private Timer timeElasped;

    private int t = 0;// creates integer variables for various purposes such as the users score
    private int counter = 0;
    private int points = 0;
    private static int finalpoints;
    private static int finaltime;

    private boolean immunity = false;

    public frame() {// constructor
        this.setTitle("Ready to play a game?");// titles the frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        board = new Board();// creates the board
        getContentPane().add(board, BorderLayout.CENTER);
        getPreferredSize();

        start = new JButton("Start");// creates the start button
        start.setPreferredSize(new Dimension(55, 40));
        start.addActionListener(new START());// adds an action listener to the button

        quit = new JButton("Quit");// creates the quit button
        quit.setPreferredSize(new Dimension(55, 40));
        quit.addActionListener(new QUIT());// adds an action listener to the button

        pause = new JButton("Pause");// creates the pause button
        pause.setPreferredSize(new Dimension(55, 40));
        pause.addActionListener(new PAUSE());// adds an action listener to the button

        maze = new JButton("Create Maze");// creates the create maze button
        maze.setPreferredSize(new Dimension(100, 40));
        maze.addActionListener(new MAZE());// adds an action listener to the button

        reset = new JButton("Reset");//creates the reset button
        reset.setPreferredSize(new Dimension(100, 40));
        reset.addActionListener(new RESET());//adds an action listener to the button
        reset.setEnabled(false);

        up = new JButton("UP");// creates the up button
        up.setPreferredSize(new Dimension(55, 40));
        up.addActionListener(new UP());// adds an action listener to the button

        down = new JButton("DOWN");// creates the down button
        down.setPreferredSize(new Dimension(55, 40));
        down.addActionListener(new DOWN());// adds an action listener to the button

        left = new JButton("LEFT");// creates the left button
        left.setPreferredSize(new Dimension(55, 40));
        left.addActionListener(new LEFT());// adds an action listener to the button

        right = new JButton("RIGHT");// creates the right button
        right.setPreferredSize(new Dimension(55, 40));
        right.addActionListener(new RIGHT());// adds an action listener to the button

        JPanel panel1 = new JPanel();// creates a new JPanel
        panel1.setPreferredSize(new Dimension(240, 45));

        panel1.add(up);// adds the up, down, left, and right button to the panel
        panel1.add(down);
        panel1.add(left);
        panel1.add(right);

        up.setEnabled(false);// disables the directional buttons
        down.setEnabled(false);
        left.setEnabled(false);
        right.setEnabled(false);

        this.add(panel1, BorderLayout.SOUTH);

        JPanel panel = new JPanel();// creates another new JPanel
        panel.setPreferredSize(new Dimension(240, 45));

        panel.add(start);// adds start, quit, pause, and maze to the new panel
        panel.add(quit);
        panel.add(pause);
        panel.add(maze);
        panel.add(reset);

        // start.setEnabled(false);//disables the start and pause button
        pause.setEnabled(false);

        this.add(panel, BorderLayout.EAST);

        JPanel panel2 = new JPanel();// creates one last panel
        panel.setPreferredSize(new Dimension(240, 200));

        textArea = new JTextArea("");// creates a textArea to display elapsed time
        textArea.setPreferredSize(new Dimension(100, 50));
        textArea.setEditable(false);

        score = new JTextArea("Score");// creates a textArea to display score
        score.setPreferredSize(new Dimension(100, 50));
        textArea.setEditable(false);

        panel2.add(textArea);// adds textAreas to the panel
        panel2.add(score);
        this.add(panel2, BorderLayout.NORTH);

        token = new Token(false);// creates a new instance of the token and adds it to the board
        token.setVisible(false);
        board.add(token);

        enemy = new Enemy(4);// creates a new instance of the enemy and adds it to the board
        enemy.setVisible(false);
        board.add(enemy);

        coin = new Coin();// creates a new instance of the coin and adds it to the board
        coin.setVisible(false);
        board.add(coin);

        immune = new Immunity();
        immune.setVisible(false);
        board.add(immune);

        portal = new Portal();
        portal.setVisible(false);
        board.add(portal);

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("space.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        catch(Exception h) {
            System.err.println(h);
        }

        this.pack();
        this.setVisible(true);

    }

    class START implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            time1 = new Timer(500, new TIME());//set up for the timers
            timeElasped = new Timer(1000, new TIMEELASPED());

            token.setVisible(true);//sets the token, enemy, immunity, and coin visible
            enemy.setVisible(true);
            coin.setVisible(true);
            immune.setVisible(true);
            portal.setVisible(true);

            up.setEnabled(true);//enables the up, down, left, and right buttons
            down.setEnabled(true);
            left.setEnabled(true);
            right.setEnabled(true);

            NextMove();

            pause.setEnabled(true);//enables the pause button
            start.setEnabled(false);//disables the start and maze button
            maze.setEnabled(false);
            reset.setEnabled(true);

            time1.start();//starts the two timers
            timeElasped.start();

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("failure.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }
            repaint();
        }
    }

    class QUIT implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            finalpoints = points;
            finaltime = t;

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("to_inf.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            try {//pauses the execution of the program
                TimeUnit.SECONDS.sleep(3);
            }
            catch(Exception hh) {
                System.err.println(hh);
            }
            System.exit(0);//closes the window and exits the program
        }
    }

    class PAUSE implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            time1.stop();//stops the timers for running
            timeElasped.stop();

            pause.setEnabled(false);//disables the pause and maze button
            start.setEnabled(true);//enables the start button
            maze.setEnabled(false);
            reset.setEnabled(true);

            up.setEnabled(false);//disables the up, down, left, and right button
            down.setEnabled(false);
            left.setEnabled(false);
            right.setEnabled(false);

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("mine.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

        }
    }

    class RESET implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            finalpoints = points;
            finaltime = t;

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("familiar.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            t = 0;
            board.removeAll();//removes everything from the board
            points = 0;
            time1.stop();
            timeElasped.stop();

            start.setEnabled(true);//sets two buttons on
            maze.setEnabled(true);

            up.setEnabled(false);//turns the directional buttons off
            down.setEnabled(false);
            left.setEnabled(false);
            right.setEnabled(false);

            pause.setEnabled(false);//turns two logistical buttons off
            reset.setEnabled(false);

            Token t2 = new Token(false);//creates a new token
            token = t2;
            board.add(token);

            Enemy e2 = new Enemy(4);//creates a new enemy
            enemy = e2;
            board.add(enemy);

            Coin c2 = new Coin();//creates a new coin
            coin = c2;
            board.add(coin);

            Immunity i2 = new Immunity();//creates a new immunity
            immune = i2;
            board.add(immune);

            Portal p2 = new Portal();//creates a new portal
            portal = p2;
            board.add(portal);

            repaint();
        }
    }

    class MAZE implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("2001_theme.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            maze.setEnabled(false);//turns off some logistical buttons
            start.setEnabled(false);
            reset.setEnabled(false);

            int[][] area = new int[25][25];

            for (int i = 0; i < area.length; i++) {
                for (int j = 0; j < area[i].length; j++) {
                    area[i][j] = 1;//sets all indexes to 1
                }
            }
            token.setVisible(true);//sets the token visible

            int x = token.getX();
            int y = token.getY();

            int w = 0;
            int one = 0;
            for (int i = 0; i < 750; i += 30) {
                if (x <= i && x >= w) {
                    x = i;//finds x coordinate for the grid
                    break;
                } 
                w = i;
                one++;
            }

            int z = 0;
            int two = 0;
            for (int i = 0; i < 750; i += 30) {
                if (y <= i && y >= z) {
                    y = i;//finds y coordinate for the grid
                    break;
                }
                z = i;
                two++;
            }

            int sum = 0;
            for (int i = 0; i < area.length; i++) {
                for (int j = 0; j < area[i].length; j++) {
                    sum += area[i][j];//sums each index of the area
                }
            }

            while (sum != 0) {
                if (area[one + 1][two] == 1) {//determines if postion has been visited
                    if (!(x + 30 > 750)) {
                        token.setLocation(x + 30, y);//sets location
                        repaint();
                        one++;
                        area[one][two] = 0;
                        sum--;
                        System.out.println("case one");
                    }
                }
                else if (area[one - 1][two] == 1) {//determines if position has been visited
                    if (!(x - 30 < 0)) {
                        token.setLocation(x - 30, y);//sets location
                        repaint();
                        one--;
                        area[one][two] = 0;
                        sum--;
                        System.out.println("case two");
                    }
                }
                else if (area[one][two + 1] == 1) {//determines if postion has been visited
                    if (!(y + 30 > 750)) {
                        token.setLocation(x, y + 30);//sets location
                        repaint();
                        two++;
                        area[one][two] = 0;
                        sum--;
                        System.out.println("case three");
                    }
                }
                else if (area[one][two - 1] == 1) {//determines if postion has been visited
                    if (!(y - 30 < 0)) {
                        token.setLocation(x, y - 30);//sets location
                        repaint();
                        two--;
                        area[one][two] = 0;
                        sum--;
                        System.out.println("case four");
                    }
                }

                /*sum = 0;
                for (int i = 0; i < area.length; i++) {
                    for (int j = 0; j < area[i].length; j++) {
                        sum += area[i][j];
                        System.out.println("case five");
                    }
                }*/
                repaint();
            }
            repaint();
            pack();
            setVisible(true);
        }
    }

    class UP implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            token.setLocation(token.getX(), token.getY() - 30);//moves token
            
            NextMove();//calls method to determine which directions are valid

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("smb_jump-small.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            if (token.getX() == immune.getX() && token.getY() == immune.getY()) {//checks if user gets immunity
                token.setImmunity(true);
                immunity = true;
                board.remove(immune);
            }

            if (token.getX() == portal.getX() && token.getY() == portal.getY()) {//checks if user found portal
                Random random = new Random();

                int x = random.nextInt(720);
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
                token.setLocation(x, y);
                board.remove(portal);
            }

           /* for (int i = 0; i < list.size(); i++) {
                if (token.getX() == list.get(i).getX() && token.getY() == list.get(i).getY()) {
                    coin.setVisible(false);
                    board.remove(coin);
                    points++;
                }  
            }

            if (token.getX() == coin.getX() && token.getY() == coin.getY()) {
                coin.setVisible(false);
                board.remove(coin);
                points++;
            }  */

            repaint();         
        }
    }

    class DOWN implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            token.setLocation(token.getX(), token.getY() + 30);//moves token

            NextMove();//calls method to determine which directions are valid

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("smb_jump-small.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            if (token.getX() == immune.getX() && token.getY() == immune.getY()) {//checks if user gets immunity
                token.setImmunity(true);
                immunity = true;
                board.remove(immune);
            }

            if (token.getX() == portal.getX() && token.getY() == portal.getY()) {//checks if user found portal
                Random random = new Random();
                
                int x = random.nextInt(720);
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
                token.setLocation(x, y);
                board.remove(portal);
            }

           /* for (int i = 0; i < list.size(); i++) {
                if (token.getX() == list.get(i).getX() && token.getY() == list.get(i).getY()) {
                    coin.setVisible(false);
                    board.remove(coin);
                    points++;
                }  
            }

            if (token.getX() == coin.getX() && token.getY() == coin.getY()) {
                coin.setVisible(false);
                board.remove(coin);
                points++;
            }  */

            repaint();
        }
    }

    class LEFT implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            token.setLocation(token.getX() - 30, token.getY());//moves token

            NextMove();//calls method to determine which directions are valid

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("smb_jump-small.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            if (token.getX() == immune.getX() && token.getY() == immune.getY()) {//checks if user gets immunity
                token.setImmunity(true);
                immunity = true;
                board.remove(immune);
            }

            if (token.getX() == portal.getX() && token.getY() == portal.getY()) {//checks if user found portal
                Random random = new Random();
                
                int x = random.nextInt(720);
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
                token.setLocation(x, y);
                board.remove(portal);
            }

            /*for (int i = 0; i < list.size(); i++) {
                if (token.getX() == list.get(i).getX() && token.getY() == list.get(i).getY()) {
                    coin.setVisible(false);
                    board.remove(coin);
                    points++;
                }  
            }

            if (token.getX() == coin.getX() && token.getY() == coin.getY()) {
                coin.setVisible(false);
                board.remove(coin);
                points++;
            }  */

            repaint();
        }
    }

    class RIGHT implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            token.setLocation(token.getX() + 30, token.getY());//moves token

            NextMove();//calls method to determine which directions are valid

            try {//plays audio
                AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("smb_jump-small.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            }
            catch(Exception h) {
                System.err.println(h);
            }

            if (token.getX() == immune.getX() && token.getY() == immune.getY()) {//checks if user gets immunity
                token.setImmunity(true);
                immunity = true;
                board.remove(immune);
            }

            if (token.getX() == portal.getX() && token.getY() == portal.getY()) {//checks if user found portal
                Random random = new Random();
                
                int x = random.nextInt(720);
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
                token.setLocation(x, y);
                board.remove(portal);
            }

            /*for (int i = 0; i < list.size(); i++) {
                if (token.getX() == list.get(i).getX() && token.getY() == list.get(i).getY()) {
                    coin.setVisible(false);
                    board.remove(coin);
                    points++;
                }  
            }

            if (token.getX() == coin.getX() && token.getY() == coin.getY()) {
                coin.setVisible(false);
                board.remove(coin);
                points++;
            }  */

            repaint();
        }
    }

    public void NextMove() {
        if (token.getY() - 30 > 730 || token.getY() - 30 < 0) {
            up.setEnabled(false);//turns off the up button if the token is already at the top of the board
         }
         else {
             up.setEnabled(true);
         }
         if (token.getY() + 30 > 730 || token.getY() + 30 < 0) {
            down.setEnabled(false);//turns off the down button if the token is already at the bottom of the board
         }
         else {
             down.setEnabled(true);
         }
         if (token.getX() - 30 > 730 || token.getX() - 30 < 0) {
            left.setEnabled(false);//turns off the left button if the token is already at the left most side of the board
         }
         else {
            left.setEnabled(true);
         }
         if (token.getX() + 30 > 730 || token.getX() + 30 < 0) {
            right.setEnabled(false);//turns off the right button if the token is already at the right most side of the board
         }
         else {
             right.setEnabled(true);
         }
    }

    class TIME implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Random random = new Random();
            int x = random.nextInt(3);
            if (x == 0) {//case 1 for direction of enemy
                enemy.setDirection(3);
                if (enemy.getX() + 30 > 720) {
                    x = random.nextInt(3);
                    enemy.setDirection(4);
                    enemy.setLocation(enemy.getX() - 30, enemy.getY());
                    repaint();
                }
                else {
                    enemy.setLocation(enemy.getX() + 30, enemy.getY());
                    repaint();
                }
            }
            else if (x == 1) {//case 2 for direction of enemy
                enemy.setDirection(1);
                if (enemy.getX() - 30 < 0) {
                    x = random.nextInt(3);
                    enemy.setDirection(3);
                    enemy.setLocation(enemy.getX() + 30, enemy.getY());
                    repaint();
                }
                else {
                    enemy.setLocation(enemy.getX() - 30, enemy.getY());
                    repaint();
                }
            }
            else if (x == 2) {//case 3 for direction of enemy
                enemy.setDirection(4);
                if (enemy.getY() + 30 > 720) {
                    x = random.nextInt(3);
                    enemy.setDirection(2);
                    enemy.setLocation(enemy.getX(), enemy.getY() - 30);
                    repaint();
                }
                else {
                    enemy.setLocation(enemy.getX(), enemy.getY() + 30);
                    repaint();
                }
            }
            else if (x == 3) {//case 4 for direction of enemy
                enemy.setDirection(2);
                if (enemy.getY() - 30 < 0) {
                    x = random.nextInt(3);
                    enemy.setDirection(4);
                    enemy.setLocation(enemy.getX(), enemy.getY() + 30);
                    repaint();
                }
                else {
                    enemy.setLocation(enemy.getX(), enemy.getY() - 30);
                    repaint();
                }
            }

            if ((enemy.getX() == token.getX() && enemy.getY() == token.getY()) && immunity == false) {//player is killed by enemy
                time1.stop();
                timeElasped.stop();
                up.setEnabled(false);
                down.setEnabled(false);
                left.setEnabled(false);
                right.setEnabled(false);
                pause.setEnabled(false);
                token.setVisible(false);
                coin.setVisible(false);
                enemy.setVisible(false);
               setTitle("GAME OVER");

               finalpoints = points;
               finaltime = t;

               try {//plays audio
                    AudioInputStream audio = AudioSystem.getAudioInputStream(this.getClass().getResource("smb_mariodie.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audio);
                    clip.start();
                }   
                catch(Exception h) {
                    System.err.println(h);
                }
            }
            
            counter++;
            /*if (counter % 5 == 0) {
                board.add(new Enemy(4));
                setVisible(true);
            }*/
        }
    }

    class TIMEELASPED implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String time = Integer.toString(t) + " seconds";
            textArea.setText(time);//prints the time elapsed 
            t++;

            String total = "Points: " + Integer.toString(points);
            score.setText(total);//prints th points earned

            Coin temp = new Coin();

            if (t % 15 == 0) {//creates a new coin every 15 seconds
                temp.setLocation(enemy.getX(), enemy.getY());
                board.add(temp);
                setVisible(true);
            }

            if (immunity == true) {
                counter++;
            }

            if (counter == 15) {//keeps track of how long user has had immunity 
                token.setImmunity(false);//immunity wears off
                counter = 0;
            }
        }
    }

    public static void main(String[] argv) {
        frame gui = new frame();

        try {
            usingFileWriter();//print result of game to file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void usingFileWriter() throws IOException {
        String content = "\nTime elapsed: " + Integer.toString(finaltime) + " seconds and points earned: " + Integer.toString(finalpoints);

        StringBuilder words = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("TopScores.txt"));//reads file
            String currentLine;
            while((currentLine = br.readLine()) != null) {
                words.append(currentLine).append("\n");
            }
        }
        catch(Exception e) {
            System.err.println(e);
        }
        content += words.toString();//combines previously saved data with new data
        FileWriter fileWriter = new FileWriter("TopScores.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(content);//prints string to file
        printWriter.close();
    }
}
