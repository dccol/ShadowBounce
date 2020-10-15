import bagel.Image;
import bagel.util.Point;

/**
 * Represents a bucket in which Ball can land as it leaves the screen, to grant an extra shot to the Player
 */
public class Bucket extends GameObject {
    /**
     * Constant describing the speed for the bucket
     */
    private static final double BUCKET_VELOCITY = 4;

    /**
     * X value of Bucket velocity
     */
    private double bucketVelocityX = BUCKET_VELOCITY;

    public Bucket(Point position, Image image, boolean bool) {
        super(position, image, bool);
    }

    public double getBucketVelocityX() {
        return this.bucketVelocityX;
    }

    public void setBucketVelocityX(double bucketVelocityX){
        this.bucketVelocityX = bucketVelocityX;
    }

    /**
     * If bucket reaches edge of screen, reverses X Velocity
     * @param left
     * @param right
     */
    public void reachedBoundary(double left, double right) {
        if (getPosition().x <= left || getPosition().x >= right) {
            reverseVelocityX();
        }
    }

    /**
     * Update Bucket position
     * Adds Buckets X Velocity to current position
     */
    public void move(){
        Point newPosition = new Point(getPosition().x + bucketVelocityX, getPosition().y);
        setPosition(newPosition);
        getBoundingBox().moveTo(newPosition);
    }

    /**
     * Reverses Bucket X Velocity
     */
    public void reverseVelocityX() {
        setBucketVelocityX(-getBucketVelocityX());
    }
}
