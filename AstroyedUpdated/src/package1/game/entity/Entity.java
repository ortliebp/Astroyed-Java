package package1.game.entity;

import package1.game.GUI;
import package1.game.Game;
import package1.game.gameUtil.Movement;

import java.awt.*;

/**
 * Created by tyleranson on 3/15/16.
 */
public abstract class Entity {
    /**
     * Where the entity is located
     */
    protected Movement position;

    /**
     * The speed of the entity in a direction
     */
    protected Movement speed;

    /**
     * The rotation of the entity
     */
    protected double rotation;

    /**
     * the size of the object used to determine where it
     * can collide with another object
     */
    protected double magnitude;

    /**
     * finds objects that are able to be removed
     */
    protected boolean deadObject;


    /**
     * This will create a new entity.
     *
     * @param position  The location of the Entity
     * @param speed     The speed that the entity is moving in a given direction
     * @param magnitude The size of the entity, will be used to determine collisions
     */
    public Entity(Movement position, Movement speed, double magnitude) {
        this.position = position;
        this.speed = speed;
        this.magnitude = magnitude;
        this.rotation = 0.0f;
        this.deadObject = false;
    }

    /******************************************************************
     * Roates the ship by the given amount (think degrees)
     * adds the requested rotation to the current position
     *
     * @param degree an amount of rotation
     *****************************************************************/
    public void rotate(double degree) {
        this.rotation += degree;
        this.rotation %= Math.PI * 2;
    }

    /******************************************************************
     * gets where the object currently is
     *
     * @return the position of the entity
     *****************************************************************/
    public Movement getPosition() {
        return position;
    }

    /******************************************************************
     * @return
     *****************************************************************/
    public Movement getSpeed() {
        return speed;
    }

    /**
     * @return
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * @param rotation
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /**
     * @return
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * can tell the game that the object should be removed
     */
    public void killObject() {
        this.deadObject = true;
    }

    public boolean isDeadObject() {
        return deadObject;
    }

    /**
     * @param game
     */
    public void update(Game game) {
        position.add(speed);
        if (position.x < 0.0f) {
            position.x += GUI.WORLD_SIZE;
        }
        if (position.y < 0.0f) {
            position.y += GUI.DWORLD_SIZE;
        }
        position.x %= GUI.WORLD_SIZE;
        position.y %= GUI.DWORLD_SIZE;
    }

    public boolean isIntercepting (Entity entity){

    double radius = entity.getMagnitude() + getMagnitude();
    return(position.getDistanceToSquared(entity.position) < radius *radius);
    }

    public abstract void handleInterception(Game game, Entity ent);
    /**
     *
     * @param g
     * @param game
     */
    public abstract void draw(Graphics2D g, Game game);
}
