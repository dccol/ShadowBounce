import bagel.Image;
import bagel.util.Point;

/**
 * Represents a type of Peg, which the Ball is unable to destroy
 */
public class GreyPeg extends Peg {

    public GreyPeg(Point position, Image image, boolean bool, String filepath) {
        super(position, image, bool, filepath);
    }
}
