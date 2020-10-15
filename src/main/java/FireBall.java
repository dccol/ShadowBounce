import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Represents a special type of Ball that spawns as a PowerUp is destroyed
 *  On collision with a Peg, all Peg's within a 70 pixel radius are also destroyed
 */
public class FireBall extends Ball{
    /**
     * Constant describing the radius of Peg destruction when FireBall collides with a Peg
     */
    private static final int BLAST_RADIUS = 70;

    public FireBall(Point position, Image image, boolean bool) {
        super(position, image, bool);
    }

    /**
     * Checks whether Ball collides with a Peg, and 'destroys' the Peg, if not GreyPeg
     *  All non-GreyPeg's within collision are also destroyed
     *  FireBall does not deflect off Pegs
     * @param pegs
     * @param numRedPegs
     * @param balls
     * @return Number of RedPegs left on board
     */
    @Override
    public int collisionPeg(ArrayList<Peg> pegs, int numRedPegs, ArrayList<Ball> balls) {
        for (Peg peg : pegs) {
            if (peg.getVisible()) {
                if (peg.getBoundingBox().intersects(getBoundingBox())) {

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

                    numRedPegs = explosion(pegs, numRedPegs, balls);
                }
            }
        }
        return numRedPegs;
    }

    /**
     * Destroys all non-GreyPegs within 70 pixels off collision
     * @param pegs
     * @param numRedPegs
     * @param balls
     * @return Number of RedPegs remaining on the board
     */
    public int explosion(ArrayList<Peg> pegs, int numRedPegs, ArrayList<Ball> balls){

        for(Peg peg: pegs) {

            if(peg.getVisible()) {

                // If within 70 pixels destroy
                if (Math.sqrt(Math.pow((getPosition().x - peg.getPosition().x), 2) +
                        Math.pow((getPosition().y - peg.getPosition().y), 2)) <= BLAST_RADIUS) {

                    if (!(peg instanceof GreyPeg)) {
                        peg.setVisible(false);
                    }
                    if (peg instanceof RedPeg) {
                        numRedPegs--;
                        //System.out.println(numRedPegs);
                    }
                    if (peg instanceof GreenPeg) {
                        ((GreenPeg) peg).spawnLeft(balls.get(1));
                        ((GreenPeg) peg).spawnRight(balls.get(2));
                    }
                }
            }
        }
        //setVisible(false);
        return numRedPegs;
    }


}
