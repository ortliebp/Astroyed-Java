package package1.game.entity;

import package1.game.Game;

import java.awt.*;
import java.util.Random;

import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;

/**
 * Created by tyleranson on 3/16/16.
 */
public class killerAsteroid extends Entity{

    protected static int size = 50;
    Random rand = new Random();
    double randNum = -200 + (200 + 200) * rand.nextDouble();
    private static final double DEF_ROTATION = -Math.PI;

    public killerAsteroid(Movement position, Movement speed, double magnitude){
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

             if(ent.getClass() == Bullet.class){
                 SoundEffect.BREAK.play();
                killObject();
                ent.killObject();
                Random rand = new Random();
                double randomNumber = -3 + (3 + 3) * rand.nextDouble();
                double randomNumber2 = -3 + (3 + 3) * rand.nextDouble();
                SmallKiller a = (new SmallKiller(new Movement(ent.getPosition()), new Movement(randomNumber, randomNumber2), SmallKiller.getSize()));
                double randomNumber3 = -3 + (3 + 3) * rand.nextDouble();
                double randomNumber4 = -3 + (3 + 3) * rand.nextDouble();
                SmallKiller b = (new SmallKiller(new Movement(ent.getPosition()), new Movement(randomNumber3, randomNumber4), SmallKiller.getSize()));
                 double randomNumber5 = -3 + (3 + 3) * rand.nextDouble();
                 double randomNumber6 = -3 + (3 + 3) * rand.nextDouble();
                 killerAsteroid c = (new killerAsteroid(new Movement(rand.nextInt(1200),rand.nextInt(900)), new Movement(randomNumber5, randomNumber6), killerAsteroid.getSize()));

                 game.registerEntity(a);
                 game.registerEntity(b);
                game.registerEntity(c);
                 game.addPoints(10);
//                if(ent.getClass() == Bullet.class){
//                    Collectable c = (new Collectable(this.getPosition(), this.getSpeed(), Collectable.getSize()));
//                    game.registerEntity(c);
//                }

            }
    }

    public void draw(Graphics2D g, Game game) {

//        int xpoints[] = {0,10,10,-10,-8,-12 -15};
//        int ypoints[] = {0,10,20,21,10,7, 8};
//
//        g.drawPolygon(xpoints, ypoints, xpoints.length); //Draw the Collectable.
//        g.fillPolygon(xpoints, ypoints, xpoints.length);
        g.setColor (Color.BLACK);
//        g.fill3DRect (size,size,size,size, true);

//        g.draw3DRect (200,200,200,200, false);
        g.fill3DRect (-size/2,-size/2,size,size, true);
//        g.fill3DRect (20,20,20,20, true);
        rotate(DEF_ROTATION/randNum);
    }
}