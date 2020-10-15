import java.lang.Math;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;


/**
 * Represents an object, that when destroyed, causes the Ball to turn into a FireBall
 */
public class PowerUp extends GameObject implements Moveable{

    /**
     * Speed of PowerUp velocity
     */
    private static final int VELOCITY = 3;
    /**
     * The X value of PowerUps velocity
     */
    private double velocityX;
    /**
     * The Y value of PowerUps velocity
     */
    private double velocityY;
    /**
     * The Point the PowerUp travels to on the screen
     */
    private Point target;



    public PowerUp(Point position, Image image, boolean bool) {
        super(position, image, bool);
    }

    // Getters
    public double getVelocityX() {
        return this.velocityX;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public Point getTarget(){
        return this.target;
    }


    //Setters
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setTarget(Point target){
        this.target = target;
    }

    /**
     * Updates PowerUp position
     * Adds PowerUp's velocity to current position
     * If PowerUp gets within 5 pixels of its random destination, chooses new location to travel to
     */
    public void move(){
        Point newPosition = new Point(getPosition().x + getVelocityX(), getPosition().y + getVelocityY());
        setPosition(newPosition);
        getBoundingBox().moveTo(newPosition);
        if(Math.sqrt(Math.pow((getTarget().x - getPosition().x), 2) +
                Math.pow((getTarget().y - getPosition().y), 2)) <= 5){
            randomVelocity();
        }
    }

    /**
     * 1 in 10 chance to spawn a PowerUp at a random location on screen
     */
    public void spawn() {
        int num = (int) (Math.random() * 9);
        if (num == 1) {
            double x = Math.random() * 1024;
            double y = 100 + Math.random() * (668);
            Point randPosition = new Point(x, y);
            setPosition(randPosition);
            getBoundingBox().moveTo(randPosition);
            setVisible(true);
            randomVelocity();
        }
    }

    /**
     * Sets a random target location for PowerUp to travel to, and initializes velocity in that direction
     */
    public void randomVelocity(){
        double targetX = Math.random() * 1024;
        double targetY = 100 + Math.random() * (668);
        Point target = new Point(targetX, targetY);
        setTarget(target);

        Vector2 direction = new Vector2(targetX - getPosition().x, targetY - getPosition().y);
        direction = direction.div(direction.length());
        direction = direction.mul(VELOCITY);

        setVelocityX(direction.x);
        setVelocityY(direction.y);
    }


}
