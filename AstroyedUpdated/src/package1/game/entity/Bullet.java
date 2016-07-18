package package1.game.entity;

import package1.game.Game;
import package1.game.SoundEffect;
import package1.game.gameUtil.Movement;

import java.awt.*;
import java.io.BufferedReader;
import java.util.Random;

/**
 * Created by Charles on 4/12/2016.
 */
public class Bullet extends Entity {

    private static final int MAX_LIFE = 50;
    private int lifespan;

    protected static int size = 4;
    protected static double origin = -(size/2.0);

    public Bullet(Entity owner, double angle){
        super(new Movement(owner.position), new Movement(angle).scale(6.75),2.0);
        this.rotation = 5.0f;
        this.lifespan = MAX_LIFE;
        this.speed.addSpeed(owner.speed);
    }

    public static int getSize(){
        return size;
    }

    public void update(Game game){
        super.update(game);
        this.lifespan--;
        if(lifespan <= 0){
            killObject();
        }
//        SoundEffect.SHOT.play();
    }
    @Override
    public void handleInterception(Game game, Entity ent){
        if(ent.getClass() == killerAsteroid.class){
            killObject();
            ent.killObject();
        }
    }

    public void draw(Graphics2D g, Game game) {

        Random rnd = new Random();
        int rc = rnd.nextInt(255)+1;
        int rc2 = rnd.nextInt(255)+1;
        int rc3 = rnd.nextInt(255)+1;
        Color j = new Color(rc,rc2,rc3);

        g.setColor (Color.red);
        g.draw3DRect (size/2, size/2 ,size,size, true);

        g.fill3DRect (size/2, size/2 ,size,size, true);
    }
}

