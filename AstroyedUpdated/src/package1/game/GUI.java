package package1.game;

import jdk.nashorn.internal.runtime.WithObject;
import package1.game.Game;
import package1.game.entity.Entity;
import package1.game.gameUtil.Movement;

import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/**********************************************************************
 * GUI class for the asteroid sets up the JPanel and JFrame for the
 * asteroid game and calls on player and asteroid to instantiate the
 * new objects.
 * Created by tyleranson on 2/15/16.
 *********************************************************************/
public class GUI extends JPanel implements ActionListener{



    private Game game;

    private JPanel gameContainer;
    private CardLayout screens;

    private JFrame gameFrame;

    /** menuBar object**/
    private JMenuBar menuBar;
    private JMenu File;
    private JMenuItem restart, exit;

    // title screen
    private JPanel titleScreen;
    private JLabel astroyd, options, cyd, ruready;
    private JButton startButton, optionsButton, exitButton;

    // start screen
    private JPanel startScreen;
    private JButton modeButton, backButton;

    // mode selection screen
    private JPanel modeScreen;
    private JButton timeAttackButton, survivalButton, insaneButton;

    //time attack screen
    private JPanel timeAttackScreen;

    //dimension of buttons
    private final Dimension D = new Dimension (200 , 100);


    /**
     * The size of the world
     */
    public static final int WORLD_SIZE = 1900;
    public static final int DWORLD_SIZE = 1000;

    /******************************************************************
     * GUI constructor (default)  instantiates a new asteroid and a
     * player
     *****************************************************************
     * @param game*/
    public GUI(Game game) {


        this.game = game;

        setPreferredSize(new Dimension(WORLD_SIZE, DWORLD_SIZE));
        setBackground(Color.GRAY);

        //initialize main screen
        screens = new CardLayout();
        gameContainer = new JPanel();

        buttons();
        screenViews();
        menu();

        //Frame
        gameFrame = new JFrame();
        gameFrame.setTitle("ASTROYED");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setMinimumSize(new Dimension(WORLD_SIZE, DWORLD_SIZE));
        gameFrame.setMaximumSize(new Dimension(WORLD_SIZE, DWORLD_SIZE));

        //Game Screens
        gameContainer.setLayout(screens);
        gameContainer.add(titleScreen, "title");
        gameContainer.add(startScreen, "start");
        gameContainer.add(modeScreen, "mode");
        gameContainer.add(timeAttackScreen, "time");

        //Pack
        gameFrame.add(gameContainer);
        gameFrame.setJMenuBar(menuBar);
        gameFrame.pack();
        gameFrame.setVisible(true);

    }

    public void menu(){

        //Menu
        File = new JMenu("File");
        restart = new JMenuItem("New");
        exit = new JMenuItem("Exit");
        menuBar = new JMenuBar();
        menuBar.add(File);
        File.add(restart);
        File.add(exit);
        restart.addActionListener(this);
        exit.addActionListener(this);
    }

    public void buttons(){

//        Insets r = new Insets(40,50,60,70);

        //title screen buttons
        startButton = new JButton("START");
        startButton.addActionListener(this);
        startButton.setPreferredSize(D);
        startButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        startButton.setBackground(Color.red);
        startButton.setFocusPainted(false);
        startButton.setForeground(Color.blue);
        startButton.setFocusable(true);
        startButton.setContentAreaFilled(true);

//        startButton.setMargin(r);

        optionsButton = new JButton("OPTIONS");
        optionsButton.addActionListener(this);
        optionsButton.setPreferredSize(D);
        optionsButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        optionsButton.setBackground(Color.red);
        optionsButton.setFocusPainted(true);
        optionsButton.setForeground(Color.blue);
        optionsButton.setFocusable(true);

        exitButton = new JButton("EXIT");
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(D);
        exitButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        exitButton.setBackground(Color.red);
        exitButton.setFocusPainted(true);
        exitButton.setForeground(Color.blue);
        exitButton.setFocusable(true);

        //start screen buttons
        modeButton = new JButton("MODE");
        modeButton.addActionListener(this);
        modeButton.setPreferredSize(D);
        modeButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        modeButton.setBackground(Color.red);
        modeButton.setFocusPainted(true);
        modeButton.setForeground(Color.blue);
        modeButton.setFocusable(true);

        backButton = new JButton("BACK");
        backButton.addActionListener(this);
        backButton.setPreferredSize(D);
        backButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        backButton.setBackground(Color.red);
        backButton.setFocusPainted(true);
        backButton.setForeground(Color.blue);
        backButton.setFocusable(true);

        //mode screen buttons
        timeAttackButton = new JButton("TIME ATTACK");
        timeAttackButton.addActionListener(this);
        timeAttackButton.setPreferredSize(D);
        timeAttackButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        timeAttackButton.setBackground(Color.red);
        timeAttackButton.setFocusPainted(true);
        timeAttackButton.setForeground(Color.blue);
        timeAttackButton.setFocusable(true);

        survivalButton = new JButton("SURVIVAL");
        survivalButton.addActionListener(this);
        survivalButton.setPreferredSize(D);
        survivalButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        survivalButton.setBackground(Color.red);
        survivalButton.setFocusPainted(true);
        survivalButton.setForeground(Color.blue);
        survivalButton.setFocusable(true);

        insaneButton = new JButton("INSANE-O");
        insaneButton.addActionListener(this);
        insaneButton.setPreferredSize(D);
        insaneButton.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.black, 15),
                        BorderFactory.createLineBorder(Color.cyan, 5)));
        insaneButton.setBackground(Color.red);
        insaneButton.setFocusPainted(true);
        insaneButton.setForeground(Color.blue);
        insaneButton.setFocusable(true);

    }

    public void screenViews(){

        //GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 20;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.weighty = 1;
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridy = 50;
        gbc1.gridx = 0;
        gbc1.anchor = GridBagConstraints.CENTER;
//        gbc1.weighty = 1;
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridy = 80;
        gbc2.gridx = 0;
        gbc2.anchor = GridBagConstraints.CENTER;
//        gbc2.weighty = 1;
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.anchor = GridBagConstraints.NORTH;
//        gbc3.gridx = 0;
//        gbc3.gridx = 0;
//        gbc3.weighty = 0;





        // Titles
        astroyd  = new JLabel();
        astroyd.setText("ASTROYED");
        astroyd.setFont(new Font("Serif", Font.BOLD , 60));
        astroyd.setSize(200,200);
        astroyd.setForeground(Color.BLUE);

        options  = new JLabel();
        options.setText("OPTIONS");
        options.setFont(new Font("Sans_Serif", 5 , 60));
        options.setSize(200,200);
        options.setForeground(Color.red);

        cyd  = new JLabel();
        cyd.setText("CHOOSE YOUR DESTINY!!!");
        cyd.setFont(new Font("Courier", Font.BOLD , 60));
        cyd.setSize(200,200);
        cyd.setForeground(Color.red);

        ruready  = new JLabel();
        ruready.setText("READY???");
        ruready.setFont(new Font("Serif", Font.ITALIC , 60));
        ruready.setSize(200,200);
        ruready.setForeground(Color.yellow);

        //title screen view
        titleScreen = new JPanel(new GridBagLayout());
        titleScreen.setBackground(Color.black);
        titleScreen.add(astroyd);
        titleScreen.add(startButton, gbc);
        titleScreen.add(optionsButton, gbc1);
        titleScreen.add(exitButton, gbc2);


        //start screen view
        startScreen = new JPanel(new GridBagLayout());
        startScreen.setBackground(Color.black);
        startScreen.add(ruready);
        startScreen.add(modeButton, gbc);
        startScreen.add(backButton, gbc1);

        //mode screen view
        modeScreen = new JPanel(new GridBagLayout());
        modeScreen.setBackground(Color.BLACK);
        modeScreen.add(cyd);
        modeScreen.add(timeAttackButton, gbc);
        modeScreen.add(survivalButton, gbc1);
        modeScreen.add(insaneButton, gbc2);

        //time attack screen view
        timeAttackScreen = new JPanel(new BorderLayout());

//        Game g = new Game();
//        timeAttackScreen.add(g);
    }

    /******************************************************************
     *
     * @param e
     *****************************************************************/
    @Override
    public void actionPerformed(ActionEvent e){

        Object clickTarget = e.getSource();

        if(exit == clickTarget){
            System.exit(0);
        }
        if(startButton == clickTarget){
            screens.show(gameContainer, "start");
            SoundEffect.SHOT.play();
        }
        if(exitButton == clickTarget){
            System.exit(0);
        }
        if(modeButton == clickTarget){
            screens.show(gameContainer, "mode");
        }
        if(backButton == clickTarget){
            screens.show(gameContainer, "title");
        }
        if(timeAttackButton == clickTarget){
            screens.show(gameContainer, "time");
        }
        if(restart == clickTarget){
            screens.show(gameContainer, "title");
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK); //Set the draw color to white.
        AffineTransform identity = g2d.getTransform();

        g.drawString("Score: " + game.getScore(), WORLD_SIZE/2, 20);
        g.drawString("High Score: " + game.getHighScore(), WORLD_SIZE/2, 40);
        g.drawString("Combo: " + game.getCombo(), WORLD_SIZE/2, 60);
        g.drawString("Deaths: " + game.getDeathCount(), WORLD_SIZE/2, 80);

        Iterator<Entity> iter = game.getEntities().iterator();
        while(iter.hasNext()) {
            Entity entity = iter.next();
            Movement position = entity.getPosition();
            drawEntity(g2d, entity, position.x, position.y);
            g2d.setTransform(identity);
        }
    }

    private void drawEntity(Graphics2D g2d, Entity entity, double x, double y) {

        g2d.translate(x, y);
        double rotation = entity.getRotation();
        if(rotation != 0.0f) {
            g2d.rotate(entity.getRotation());
        }
        entity.draw(g2d, game);
    }
}
