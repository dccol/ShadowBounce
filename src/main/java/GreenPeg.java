import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * Represents a type of Peg that when destroyed by the Ball, spawns two extra Balls
 */
public class GreenPeg extends Peg {

    public GreenPeg(Point position, Image image, boolean bool, String filepath) {
        super(position, image, bool, filepath);
    }

    /**
     * Replaces GreenPeg with a BluePeg
     * @param pegs
     */
    public void createBluePeg(ArrayList<Peg> pegs) {
        Point bluePosition = this.getPosition();
        String blueShape = determineShape(this.getFilepath());
        String blueFilepath = "res/" + blueShape + "peg.png";
        Image blueImage = new Image(blueFilepath);
        BluePeg bluePeg = new BluePeg(bluePosition, blueImage, true, blueFilepath);
        pegs.remove(this);
        pegs.add(bluePeg);
    }

    /**
     * Determines the shape of Peg
     * @param filepath
     * @return
     */
    @Override
    public String determineShape(String filepath) {
        if (filepath.contains("horizontal")) {
            return "horizontal-";
        } else if (filepath.contains("vertical")) {
            return "vertical-";
        } else {
            return "";
        }
    }

    /**
     * Spawns extra Ball travelling diagonally left upwards, from GreenPeg position
     * @param ball
     */
    public void spawnLeft(Ball ball){
        ball.setPosition(getPosition());
        ball.extraBallLeftVelocity();
        ball.setVisible(true);
    }

    /**
     * Spawns extra Ball travelling diagonally right upwards, from GreenPeg position
     * @param ball
     */
    public void spawnRight(Ball ball) {
        ball.setPosition(getPosition());
        ball.extraBallRightVelocity();
        ball.setVisible(true);
    }
}
