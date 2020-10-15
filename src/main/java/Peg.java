import bagel.Image;
import bagel.util.Point;

/**
 * Represents a Peg that a Ball can destroy
 */
public abstract class Peg extends GameObject{

    /**
     * A String containing the file path of the Image, used to determine shape of a Peg
     */
    private String filepath;

    public Peg(Point position, Image image, boolean bool, String filepath) {
        super(position, image, bool);
        this.filepath = filepath;
    }

    public String getFilepath(){
        return this.filepath;
    }

    /**
     * Determines the Shape of Peg
     * @param filepath
     * @return
     */
    public String determineShape(String filepath){
        if(filepath.contains("horizontal")){
            return "-horizontal";
        }
        else if(filepath.contains("vertical")){
            return "-vertical";
        }
        else{
            return "";
        }
    }

}
