import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * Represents the board or level which the Player is trying to complete
 *  It is able to randomly generate GreenPeg's at the end of each turn, and keep track of how many RedPeg's remain in the level
 */
public class GameBoard {

    private int numRedPegs;
    private ArrayList<Peg> pegs;

    public GameBoard(ArrayList<Peg> pegs, int numRedPegs){
        this.pegs = pegs;
        this.numRedPegs = numRedPegs;
    }

    public ArrayList<Peg> getPegs(){
        return this.pegs;
    }

    public int getNumRedPegs(){
        return numRedPegs;
    }

    public void setNumRedPegs(int numRedPegs){
        this.numRedPegs = numRedPegs;
    }

    /**
     * Resets the state of the board once a turn ends
     * @param powerUp
     */
    public void reset(PowerUp powerUp) {
        for (Peg peg : getPegs()) {
            if (peg.getVisible() && peg instanceof BluePeg) {
                refreshGreenPeg();
                break;
            }
        }

        powerUp.setVisible(false);
    }

    /**
     * Randomizes the location of GreenPeg spawn on screen
     */
    public void refreshGreenPeg(){
        // Get rid of unused GreenPeg, replace with BluePeg
        for (Peg peg : getPegs()) {
            if (peg instanceof GreenPeg && peg.getVisible()) {

                ((GreenPeg) peg).createBluePeg(getPegs());
            }
        }

        //Create new GreenPeg
        int k = 0;
        while (k < 1) {
            int index = (int) (Math.random() * getPegs().size());
            if (getPegs().get(index) instanceof BluePeg && getPegs().get(index).getVisible()) {
                ((BluePeg) getPegs().get(index)).createGreenPeg(getPegs());
                k++;
            }
        }
    }
}
