package package1.game.gameUtil;

/**
 * Created by tyleranson on 3/16/16.
 */
public class Movement {

    /**
     * x directional movement value
     */
    public double x;

    /**
     * y directional movement value
     */
    public double y;

    public Movement(double degree) {
        this.x = Math.cos(degree);
        this.y = Math.sin(degree);
    }
    public Movement(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Movement(Movement move){
        this.x = move.x;
        this.y = move.y;
    }

    public Movement(int move){
        this.x = move;
        this.y = move;
    }

    public void addSpeed(Movement speed){
        this.x = this.x + speed.x;
        this.y = this.y + speed.y;
    }

    public Movement set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Movement scale(double factor){
        this.x *= factor;
        this.y *= factor;
        return this;
    }

    public Movement add(Movement move){
        this.x += move.x;
        this.y += move.y;
        return this;
    }

    public double getShipMagnitude() {
        return (x * x + y * y);
    }

    public Movement controlSpeed() {
        double magnitude = getShipMagnitude();
        if (magnitude != 0.0f && magnitude != 1.0f) {
            magnitude = Math.sqrt(magnitude);
            this.x /= magnitude;
            this.y /= magnitude;
        }
        return this;
    }

    public double getDistanceToSquared(Movement mvt) {
        double dx = this.x - mvt.x;
        double dy = this.y - mvt.y;
        return (dx * dx + dy * dy);
    }
}
