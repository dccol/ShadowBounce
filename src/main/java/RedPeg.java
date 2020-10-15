import bagel.Image;
import bagel.util.Point;

/**
 * Represents a type of Peg which the Player is trying to destroy with the Ball in order to advance in the game
 */
public class RedPeg extends Peg {

    public RedPeg(Point position, Image image, boolean bool, String filepath) {
        super(position, image, bool, filepath);
    }
}
