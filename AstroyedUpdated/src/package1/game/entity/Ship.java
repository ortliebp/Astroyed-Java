package package1.game.entity;

import package1.game.Game;
import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by tyleranson on 3/15/16.
 */
public class Ship extends Entity {

    private static final double DEF_ROTATION = -Math.PI / 20.0;

    private static final double THRUST_MAGNITUDE = 1;

    /**
     * The fastest our rocket ship can travel
     */
    private static final double MAX_SPEED = 8;

    /**
     * is the up button pressed
     */
    private boolean upPressed;

    /**
     * is the right button pressed
     */
    private boolean rightPressed;

    /**
     * is the left button presssed
     */
    private boolean leftPressed;

    /**
     * is the down button pressed
     */
    private boolean downPressed;

    private int animationFrame;

    private List<Bullet> bullets;

    private boolean fireBullet;

    public boolean immortal;

    private int timeImmortal;

    private final int Max_Immortal = 500;


    public Ship() {
        super(new Movement(1900 / 2, 1000 / 2), new Movement(0.0, 0.0), 10.0);
        this.rotation = DEF_ROTATION;
        this.deadObject = false;
        this.upPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
        this.downPressed = false;
        this.fireBullet = false;
        this.animationFrame = 0;
        this.bullets = new ArrayList<Bullet>();
        this.immortal = true;
        this.timeImmortal = Max_Immortal;
    }

    /******************************************************************
     * @param upPressed
     *****************************************************************/
    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    /******************************************************************
     * @param rightPressed
     */
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    /******************************************************************
     * @param leftPressed
     */
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    /******************************************************************
     * @param downPressed
     */
    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }



    public void setFiring(boolean fireBullet){
        this.fireBullet = fireBullet;
    }

    public void setImmortal(boolean immortal){
        this.immortal = immortal;
    }
    @Override
    public void update(Game game) {
        super.update(game);

        this.animationFrame++;


        if(immortal){
            timeImmortal--;
            if(timeImmortal == 0){
                setImmortal(false);
                timeImmortal = Max_Immortal;
            }
        }

        if (leftPressed != rightPressed) {
            rotate(leftPressed ? DEF_ROTATION : -DEF_ROTATION);
        }
        if (upPressed) {
            speed.add(new Movement(rotation).scale(1));
            if (speed.getShipMagnitude() >= MAX_SPEED * MAX_SPEED) {
                speed.controlSpeed().scale(MAX_SPEED);
            }
        }
        if (!upPressed && !downPressed){

            speed.scale(.975);
            SoundEffect.UH.stop();
        }
        if (downPressed){
            speed.add(new Movement(rotation).scale(-THRUST_MAGNITUDE/2));
            if (speed.getShipMagnitude() >= MAX_SPEED * MAX_SPEED) {
                speed.controlSpeed().scale(MAX_SPEED/2);
            }
            SoundEffect.UH.loop();
        }
        if (fireBullet) {
            if(game.bulletCount == 0){
                Bullet bullet = new Bullet(this, rotation);
                bullets.add(bullet);
                game.registerEntity(bullet);
                game.bulletCount++;
            }

        }
        Iterator<Bullet> iter = bullets.iterator();
            while (iter.hasNext()){
                Bullet bullet = iter.next();
                if(bullet.deadObject){
                    iter.remove();
                }
        }
    }


    @Override
    public void handleInterception(Game game, Entity ent){

        if(ent.getClass() == Collectable.class) {
            ent.killObject();
            game.addCombo(1);
            if(game.getCombo()%15 == 0){
                setImmortal(true);
                SoundEffect.COMBO.play();
            }
        }
        if(ent.getClass() == killerAsteroid.class && !immortal) {
            killObject();
            game.addDeathCount(1);
            SoundEffect.BONK.play();
            game.startGame();

        }
        if(ent.getClass() == SmallKiller.class && !immortal) {
            killObject();
            game.addDeathCount(1);
            SoundEffect.BONK.play();
            game.startGame();


        }
    }
    /**
     * @param g
     * @param game
     */
    @Override
    public void draw(Graphics2D g, Game game) {
        g.drawLine(10, -4, 15, 0);
        g.drawLine(10, 4, 15, 0);
        g.drawRect(-10, -4, 19, 8);
        g.fill3DRect(-10, -4, 19, 8, true);
        g.setColor(Color.yellow);
        g.drawLine(-10, 8, -10, 4);
        g.drawLine(-10, 8, 4, 4);
        g.drawLine(-10, -8, -10, -4);
        g.drawLine(-10, -8, 4, -4);
        if(this.immortal){
            Random rnd = new Random();
            int rc = rnd.nextInt(255)+1;
            int rc2 = rnd.nextInt(255)+1;
            int rc3 = rnd.nextInt(255)+1;
            Color j = new Color(rc,rc2,rc3);

            g.setColor (j);
            g.drawOval(-16, -16, 32, 32);
        }
    }
}


