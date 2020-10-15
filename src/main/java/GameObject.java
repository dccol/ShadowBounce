import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Point;

/**
 * Represents the basic unit of ShadowBounce objects, which can be extended by Ball, Peg, PowerUp, and Bucket
 */
public abstract class GameObject implements Renderable{
    /**
     * A Point containing the position of the object
     */
    private Point position;
    /**
     * An Image of a .png file which can be rendered on screen
     */
    private Image image;
    /**
     * A Rectangle which outlines the image and assists in collision detection
     */
    private Rectangle boundingBox;
    /**
     * A boolean describing whether the object is visible on screen or not
     */
    private boolean visible;

    public GameObject(Point position, Image image, boolean visible) {
        this.position = position;
        this.image = image;
        this.visible = visible;
        createBoundingBox();
    }



    private void createBoundingBox(){
        this.boundingBox = this.image.getBoundingBox();
        boundingBox.moveTo(this.position);
    }


    public Point getPosition(){
        return this.position;
    }

    public Image getImage(){
        return this.image;
    }

    public Rectangle getBoundingBox(){
        return this.boundingBox;
    }

    public Boolean getVisible(){
        return this.visible;
    }

    public void setPosition(Point position){
        this.position = position;
    }

    public void setVisible(boolean bool){
        this.visible = bool;
    }

    /**
     * Draw object on screen at position
     */
    public void render(){
        getImage().draw(getPosition().x, getPosition().y);
    }
}

