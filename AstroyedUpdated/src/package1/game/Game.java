package package1.game;

import package1.GameClockTimer;
import package1.game.entity.*;
import package1.game.gameUtil.Movement;

import javax.sound.sampled.*;
import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by tyleranson on 3/15/16.
 */
public class Game extends JFrame{
    private int playerLives;
    private boolean isGameOver;
    protected int numAsteroids = 0;
    protected int numKillerAstoids = 10;
    public int bulletCount = 0;
    int numCollected = 0;
    int numDeaths = 0;
      /**
     * The frames per second that the game is going to refresh at
     */
    private static final int FPS = 60;
    /**
     * The number of nanoseconds that should elapse each frame.
     */
    private static final long FRAME_TIME = (long)(1000000000.0 / FPS);
    /**
     * the GUI instance or world
     */
    private GUI gui;
    /**
     * A list of enties that are in the game currently
     */
    private List<Entity> entities;
    /**
     * A list of entities that are going to be added to the game
     */
    private List<Entity> pendingEntities;
    /**
     * The rocketShip (RocketShip)
     */
    private Ship rocketShip;
    /**
     * a clock for handling updates to the game
     */
    private GameClockTimer timer;
    public boolean gameOver;
    private int score;
    private int highScore = 0;


    public Game() {
        super("ASTROYED");

        SoundEffect.BACKGROUND.loop();

        SoundEffect.init();
        SoundEffect.volume = SoundEffect.Volume.MEDIUM;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(this.gui = new GUI(this), BorderLayout.CENTER);

        addKeyListener(new KeyAdapter(){ /******************************************************************
         * keyPressed method determines what keys are being pressed in
         * combination with others to set the rocket ship in a direction.
         * @param e
         *****************************************************************/
        @Override
        public void keyPressed(KeyEvent e) {

            int code = e.getKeyCode();
            if(code == KeyEvent.VK_UP){// && (!downPressed)){
                rocketShip.setUpPressed(true);
                SoundEffect.THRUST.loop();
            }
            if(code == KeyEvent.VK_DOWN){
                rocketShip.setDownPressed(true);
            }
            if(code == KeyEvent.VK_LEFT){
                rocketShip.setLeftPressed(true);
            }
            if(code == KeyEvent.VK_RIGHT){
                rocketShip.setRightPressed(true);
            }
            if(code == KeyEvent.VK_SPACE){
                rocketShip.setFiring(true);
                SoundEffect.SHOT.play();

            }
        }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_UP){
                    rocketShip.setUpPressed(false);
                    SoundEffect.THRUST.stop();
                }

                if(code == KeyEvent.VK_DOWN){
                    rocketShip.setDownPressed(false);
                }

                if(code == KeyEvent.VK_LEFT){
                    rocketShip.setLeftPressed(false);
                }

                if(code == KeyEvent.VK_RIGHT){
                    rocketShip.setRightPressed(false);
                }
                if (code == KeyEvent.VK_SPACE){
                    rocketShip.setFiring(false);
                    bulletCount = 0;
//                    SoundEffect.SHOT.stop();
                }
            }

        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void addPoints(int amount){
        score += amount;
    }
    public void addCombo(int combo){
        numCollected += combo;
    }
    public int getCombo(){
        return numCollected;
    }
    public int getScore(){
        return score;
    }
    public int getHighScore(){
        return highScore;
    }
    public void addDeathCount(int amount){
        numDeaths += amount;
    }
    public int getDeathCount(){
        return numDeaths;
    }
    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void addAsteroid(List<Entity> entities) {
        Random rand = new Random();
        double randomNumber = -3 + (3 + 3) * rand.nextDouble();
        double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
        pendingEntities.add(new Collectable(new Movement(rand.nextInt(1200),rand.nextInt(900)), new Movement(randomNumber,randomNumber2), Collectable.getSize()));
    }

//    public void addAsteroid(Movement position, double magnitude, Movement speed) {
//        entities.add(new Collectable(position,speed, magnitude));
//    }
    public void addKillerAsteroid(List<Entity> entities) {
        Random rand = new Random();
        double randomNumber = -3 + (3 + 3) * rand.nextDouble();
        double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
        killerAsteroid a = (new killerAsteroid(new Movement(rand.nextInt(1200), rand.nextInt(900)), new Movement(randomNumber, randomNumber2), killerAsteroid.getSize()));
        Movement shipPosition = rocketShip.getPosition();
        Movement position = a.getPosition();

        if (!((position.x > shipPosition.x-40 && position.x < shipPosition.x+40) || (position.y > shipPosition.y-40 && position.y < shipPosition.y+40)) && (!rocketShip.isIntercepting(a))) {
            pendingEntities.add(a);
        }
    }
    public void registerEntity(Entity entity) {
        pendingEntities.add(entity);
    }
    /**
     * Starts the game and keeps the game running.
     */
    public void startGame() {
        entities = new LinkedList<Entity>();
        pendingEntities = new ArrayList<Entity>();
        rocketShip = new Ship();
        if(score > highScore){
            highScore = score;
        }
        score = 0;
        numCollected = 0;



        //Sets everything back to its default values
//        resetGame();
        clearLists();
        this.timer = new GameClockTimer(FPS);
        for(int i = 0; i < numAsteroids; i++)
        {
            addAsteroid(entities);
        }
        for(int i = 0; i < numKillerAstoids; i++){
            addKillerAsteroid(entities);
        }
        while(true) {
            //Gets the initial time of the start
            long start = System.nanoTime();
            updateGame();
            timer.update();
            for (int i = 0; i < 5 && timer.hasElapsedCycle(); i++){
                //updateGame();
            }
            gui.repaint();

            long delta = FRAME_TIME - (System.nanoTime() - start);
            if(delta > 0) {
                try {
                    Thread.sleep(delta / 1000000L, (int) delta % 1000000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            for(Entity entity : entities) {
                entity.update(this);

            }
        }
    }

    private void resetGame(){
        this.playerLives = 0;
        Game g = new Game();
        g.startGame();

    }

    private void updateGame() {

        entities.addAll(pendingEntities);
        pendingEntities.clear();


        //method that detects the shimmering blockss and "collects" them
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            for (int j = 0; j < entities.size(); j++) {
                Entity b = entities.get(j);
                if (i != j && a.isIntercepting(b)) {
                    a.handleInterception(this, b);
//                    b.handleInterception(this, a);
                }
            }
        }


        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isDeadObject()) {
                entities.remove(iterator);
                iterator.remove();
            }
        }
        if (gameOver) {
            resetGame();
        }

    }


    private void clearLists(){
        pendingEntities.clear();
//        entities.clear();
        pendingEntities.add(rocketShip);
    }

    public void killPlayer(){
        this.playerLives--;
        gameOver = true;

        if(playerLives == 0){
            this.isGameOver = true;
        }
    }

    /******************************************************************
     * main method of the GUI that makes an instance of the GUI
     * @param args
     *****************************************************************/
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Game game = new Game();
        game.startGame();
    }
}
