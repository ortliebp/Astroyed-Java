package package1.game.entity;

import package1.game.Game;

import java.awt.*;
import java.util.Random;

import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;

/**
 * Created by tyleranson on 3/16/16.
 */
public class SmallKiller extends Entity{

    protected static int size = 30;
    Random rand = new Random();
    double randNum = -10 + (10 + 10) * rand.nextDouble();
    private static final double DEF_ROTATION = -Math.PI ;


    public SmallKiller(Movement position, Movement speed, double magnitude){
        super(position, speed, magnitude);
        this.deadObject = false;
        this.position = position;
        this.speed = speed;
        this.magnitude = magnitude;
        this.rotation = 5.0f;
    }

    public static int getSize(){
        return size;
    }


    @Override
    public void handleInterception(Game game, Entity ent){
//        if(ent.getClass() == Ship.class) {
//            ent.killObject();
//            SoundEffect.BONK.play();
//            game.startGame();
//        }
        if(ent.getClass() == Bullet.class){
            killObject();
            ent.killObject();
            game.addPoints(20);
//            Random rand = new Random();
//            double randomNumber = -3 + (3 + 3) * rand.nextDouble();
//            double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
//            killerAsteroid a = (new killerAsteroid(new Movement(ent.getPosition()), new Movement(randomNumber, randomNumber2), killerAsteroid.getSize()));
//            game.registerEntity(a);

            Collectable b = (new Collectable(this.getPosition(), this.getSpeed(), Collectable.getSize()));
            game.registerEntity(b);


        }
    }

    public void draw(Graphics2D g, Game game) {

//        int xpoints[] = {0,10,10,-10,-8,-12 -15};
//        int ypoints[] = {0,10,20,21,10,7, 8};
//
//        g.drawPolygon(xpoints, ypoints, xpoints.length); //Draw the Collectable.
//        g.fillPolygon(xpoints, ypoints, xpoints.length);
        g.setColor (Color.BLUE);
//        g.fill3DRect (size,size,size,size, true);

        g.draw3DRect (-size/2,-size/2,size,size, true);
        g.fill3DRect (-size/2,-size/2,size,size, true);
//        g.fill3DRect (20,20,20,20, true);
        rotate(-DEF_ROTATION/randNum);
    }
}
