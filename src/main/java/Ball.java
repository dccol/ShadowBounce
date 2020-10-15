import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
import bagel.Input;
import bagel.util.Side;

import java.util.ArrayList;

/**
 * Represents a Ball that is used to destroy Pegs
 */
public class Ball extends GameObject implements Moveable {

    /**
     * Initial speed when ball is spawned
     */
    private static final double INITIAL_BALL_VELOCITY = 10;

    /**
     * X value of Ball velocity
     */
    private double ballVelocityX;
    /**
     * Y value of Ball velocity
     */
    private double ballVelocityY;

    /**
     * Constructor Ball
     * @param position
     * @param image
     * @param bool
     */
    public Ball(Point position, Image image, boolean bool) {
        super(position, image, bool);
    }

    /**
     * Returns the value of Ball's X Velocity
     * @return ballVelocityX
     */
    public double getBallVelocityX() {
        return this.ballVelocityX;
    }

    /**
     * Returns the value of Ball's Y Velocity
     * @return ballVelocityY
     */
    public double getBallVelocityY() {
        return this.ballVelocityY;
    }

    /**
     * Update the value of Ball's X Velocity
     * @param ballVelocityX
     */
    public void setBallVelocityX(double ballVelocityX) {
        this.ballVelocityX = ballVelocityX;
    }

    /**
     * Update the value of Ball's Y Velocity
     * @param ballVelocityY
     */
    public void setBallVelocityY(double ballVelocityY) {
        this.ballVelocityY = ballVelocityY;
    }

    /**
     * Updates Balls position,
     * Adds Ball's velocity to the current position to calculate new position
      */
    public void move() {
        Point newPosition = new Point(getPosition().x + ballVelocityX, getPosition().y + ballVelocityY);
        setPosition(newPosition);
        getBoundingBox().moveTo(newPosition);
    }

    /**
     * Resets the Ball's position to its initial position and calculate its initial velocity
     * @param initialPosition
     * @param input
     */
    public void reset(Point initialPosition, Input input) {
        setVisible(true);
        setPosition(initialPosition);
        Vector2 direction = input.directionToMouse(initialPosition);
        direction = direction.mul(INITIAL_BALL_VELOCITY);
        setBallVelocityX(direction.x);
        setBallVelocityY(direction.y);
    }

    /**
     * Checks whether Ball collides with a Peg, and 'destroys' the Peg, if not GreyPeg
     * @param pegs
     * @param numRedPegs
     * @param balls
     * @return number of RedPegs remaining in the board
     */
    public int collisionPeg(ArrayList<Peg> pegs, int numRedPegs, ArrayList<Ball> balls) {
        for (Peg peg : pegs) {
            if (peg.getVisible()) {
                if (peg.getBoundingBox().intersects(getBoundingBox())) {

                    deflect();

                    if (!(peg instanceof GreyPeg)) {
                        peg.setVisible(false);
                    }
                    if (peg instanceof RedPeg) {
                        numRedPegs--;
                        //System.out.println(numRedPegs);
                    }
                    if(peg instanceof GreenPeg){
                        ((GreenPeg) peg).spawnLeft(balls.get(1));
                        ((GreenPeg) peg).spawnRight(balls.get(2));

                    }
                }
            }
        }
        return numRedPegs;
    }

    /**
     * Checks whether Ball collides with Bucket
     * @param bucket
     * @return true or false
     */
    public boolean collisionBucket(Bucket bucket) {
        if (bucket.getBoundingBox().intersects(getBoundingBox())) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether Ball collides with PowerUp
     * @param powerUp
     * @param balls
     */
    public void collisionPowerUp(PowerUp powerUp, ArrayList<Ball> balls){
        if (powerUp.getBoundingBox().intersects(getBoundingBox())){
            powerUp.setVisible(false);
            spawnFireBall((FireBall)balls.get(3));
        }
    }

    /**
     * Determines which Side a collision occurs, and reverses Ball velocity accordingly
     */
    public void deflect() {
        Vector2 ballVelocity = new Vector2(getBallVelocityX(), getBallVelocityY());
        Side collisionSide = getBoundingBox().intersectedAt(getPosition(), ballVelocity);

        if (collisionSide.equals(Side.LEFT) || collisionSide.equals(Side.RIGHT)) {
            reverseVelocityX();
        } else if (collisionSide.equals(Side.TOP) || collisionSide.equals(Side.BOTTOM)) {
            reverseVelocityY();
        }
    }

    /**
     * Checks whether Ball has reached edge of screen and reverses X Velocity if true
     * @param left
     * @param right
     */
    public void reachedBoundary(double left, double right) {
        if (getPosition().x <= left || getPosition().x >= right) {
            reverseVelocityX();
        }
    }

    // check whether actually get an extra shot or just don't lose a shot

    /**
     * Check whether Ball has left the bottom of screen
     *  If true, set Ball visibility to false
     * Check whether Ball collides with Bucket as it leaves screen
     *  If true, gain extra shot
     * Check whether Ball leaving screen signals end of turn
     *  If true, lose shot, reset board activity
     * @param bottom
     * @param shotsLeft
     * @param bucket
     * @param balls
     * @param gameBoard
     * @param powerUp
     * @return Number of shots left
     */
    public int offScreen(double bottom, int shotsLeft, Bucket bucket, ArrayList<Ball> balls, GameBoard gameBoard, PowerUp powerUp) {

        if (getPosition().y > bottom) {
            setVisible(false);

            if (collisionBucket(bucket)) {
                shotsLeft++;
            }

            // If this ball was final one to leave screen
            if (isTurnOver(balls)) {
                //System.out.println("Turn Over");
                shotsLeft--;

                gameBoard.reset(powerUp);

                // Chance to spawn PowerUp
                if (!powerUp.getVisible()) {
                    powerUp.spawn();
                }
            }
            System.out.format("%d shots remaining\n", shotsLeft);
        }
        return shotsLeft;
    }

    /**
     * Spawn a FireBall on Ball current position, replacing Ball
     * @param fireBall
     */
    public void spawnFireBall(FireBall fireBall) {
        fireBall.setPosition(getPosition());
        fireBall.setBallVelocityX(getBallVelocityX());
        fireBall.setBallVelocityY(getBallVelocityY());
        fireBall.setVisible(true);
        setVisible(false);
    }

    /**
     * Check if no Balls visible on screen, to signify end of turn
     * @param balls
     * @return true or false
     */
    public boolean isTurnOver (ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            if (ball.getVisible()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Reverse Ball X Velocity
     */
    public void reverseVelocityX() {
        setBallVelocityX(-getBallVelocityX());
    }

    /**
     * Reverse Ball Y Velocity
     */
    public void reverseVelocityY() {
        setBallVelocityY(-getBallVelocityY());
    }

    /**
     * Initialize left ExtraBall initial Velocity diagonally upwards and to the left
     */
    public void extraBallLeftVelocity(){
        Vector2 direction = Vector2.up;
        direction = direction.add(Vector2.left);
        direction = direction.mul(INITIAL_BALL_VELOCITY);
        setBallVelocityX(direction.x);
        setBallVelocityY(direction.y);

    }

    /**
     * Initialize right ExtraBall initial Velocity diagonally upwards and to the right
     */
    public void extraBallRightVelocity(){
        Vector2 direction = Vector2.up;
        direction = direction.add(Vector2.right);
        direction = direction.mul(INITIAL_BALL_VELOCITY);
        setBallVelocityX(direction.x);
        setBallVelocityY(direction.y);
    }

    public <T> int method(T[] array, T item){
        int count = 0;
        return count;
    }
}

