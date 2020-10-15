import bagel.Image;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * Represents a basic Peg type that the Ball can destroy
 */
public class BluePeg extends Peg {

    public BluePeg(Point position, Image image, boolean visible, String filepath) {
        super(position, image, visible, filepath);

    }

    /**
     * Replace BluePeg with RedPeg
     * @param pegs
     */
    public void createRedPeg(ArrayList<Peg> pegs){
        Point redPosition = this.getPosition();
        String redShape = determineShape(this.getFilepath());
        String redFilepath = "res/red" + redShape + "-peg.png";
        Image redImage = new Image(redFilepath);
        RedPeg redPeg = new RedPeg(redPosition, redImage, true, redFilepath);
        pegs.remove(this);
        pegs.add(redPeg);
    }

    /**
     * Replace BluePeg with GreenPeg
     * @param pegs
     */
    public void createGreenPeg(ArrayList<Peg> pegs) {
        Point greenPosition = this.getPosition();
        String greenShape = determineShape(this.getFilepath());
        String greenFilepath = "res/green" + greenShape + "-peg.png";
        Image greenImage = new Image(greenFilepath);
        GreenPeg greenPeg = new GreenPeg(greenPosition, greenImage, true, greenFilepath);
        pegs.remove(this);
        pegs.add(greenPeg);
    }


}
