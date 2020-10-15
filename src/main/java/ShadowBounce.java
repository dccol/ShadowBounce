import bagel.*;
import bagel.util.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;

/**
 * ShadowBounce represents the game itself, which when created and ran will launch a window for the Player to engage with
 */
public class ShadowBounce extends AbstractGame {

    // CONSTANTS
    /**
     * Constant describing X value of the left boarder of the window
     */
    private static final double LEFT_BOARDER = 0;
    /**
     * Constant describing X value of the right boarder of the window
     */
    private final double RIGHT_BOARDER = Window.getWidth();
    /**
     * Constant describing Y value of the bottom boarder of the window
     */
    private final double BOTTOM_BOARDER = Window.getHeight();

    /**
     * Constant describing X value of main Balls spawn position
     */
    private static final double BALL_STARTING_POSITION_X = 512;
    /**
     * Constant describing Y value of main Balls spawn position
     */
    private static final double BALL_STARTING_POSITION_Y = 32;

    /**
     * Constant describing X value of Buckets spawn position
     */
    private static final double BUCKET_STARTING_POSITION_X = 512;
    /**
     * Constant describing Y value of Buckets spawn position
     */
    private static final double BUCKET_STARTING_POSITION_Y = 744;
    /**
     *  Constant describing the amount the Y velocity increases by each frame
     */
    private static final double GRAVITY_CONSTANT = 0.15;
    /**
     * Constant describing the amount of shots the Player beings the game with
     */
    private static final int STARTING_SHOTS = 20;
    /**
     * Constant describing the total number of boards the game consists of
     */
    private static final int NUM_BOARDS = 5;

//----------------------------------------------------------------------------------------------------------------------

    // Peg
    /**
     * A Point variable used to instantiate a Peg
     */
    private Point pegPosition;
    /**
     * An Image variable used to instantiate a Peg
     */
    private Image pegImage;
    /**
     * An ArrayList to store instantiated Pegs
     */
    private ArrayList<Peg> pegs;
    /**
     * A variable to store the number of RedPegs in a board
     */
    private int numRedPegs;

    // Ball
    /**
     * A Point variable used to instantiate a Ball
     */
    private Point ballInitialPosition;
    /**
     * An Image variable used to instantiate a Ball
     */
    private Image ballImage;
    /**
     * An ArrayList used to store Balls
     */
    private ArrayList<Ball> balls = new ArrayList<>();
    /**
     * A variable used to track how many shots the Player has left
     */
    private int shotsLeft = STARTING_SHOTS;

    // Bucket
    /**
     * A Point variable used to instantiate Bucket
     */
    private Point bucketInitialPosition;
    /**
     * An Image variable used to instantiate Bucket
     */
    private Image bucketImage;
    /**
     * A Bucket variable used to store Bucket once it has been instantiated
     */
    private Bucket bucket;

    // PowerUp
    /**
     * A Point variable used to instantiate PowerUp
     */
    private Point powerInitialPosition;
    /**
     * An Image variable used to instantiate PowerUp
     */
    private Image powerImage;
    /**
     * A PowerUp variable used to store PowerUp once it has been instantiated
     */
    private PowerUp powerUp;

    // GameBoard
    /**
     * An Array used to store the 5 GameBoards
     */
    private GameBoard[] gameBoards = new GameBoard[NUM_BOARDS];
    /**
     * A variable used to indicate the index of the current board being played
     */
    private int currentBoardIndex = 0;
    /**
     * A variable used to store the current GameBoard being played
     */
    private GameBoard currentBoard;

//----------------------------------------------------------------------------------------------------------------------

    public ShadowBounce() {

        // Instantiate Balls
        for(int i=0; i < 3; i++) {
            ballInitialPosition = new Point(BALL_STARTING_POSITION_X, BALL_STARTING_POSITION_Y);
            ballImage = new Image("res/ball.png");
            Ball ball = new Ball(ballInitialPosition, ballImage, false);
            balls.add(ball);
        }
        // Instantiate FireBall
        ballInitialPosition = new Point(BALL_STARTING_POSITION_X, BALL_STARTING_POSITION_Y);
        ballImage = new Image("res/fireball.png");
        FireBall fireBall = new FireBall(ballInitialPosition, ballImage, false);
        balls.add(fireBall);

        // Instantiate Pegs (could use factory pattern
        for( int i = 0; i < gameBoards.length; i++) {
            try (Scanner csvReader = new Scanner(new FileReader("res/" + i + ".csv"))) {
                //System.out.format("file %d opened\n", i);
                pegs = new ArrayList<>();
                while (csvReader.hasNextLine()) {
                    String line = csvReader.nextLine();
                    String[] lineArray = line.split(",");
                    String type = lineArray[0];
                    int x = Integer.parseInt(lineArray[1]);
                    int y = Integer.parseInt(lineArray[2]);

                    pegPosition = new Point(x, y);

                    if (type.equals("blue_peg")) {
                        pegImage = new Image("res/peg.png");
                        BluePeg peg = new BluePeg(pegPosition, pegImage, true, "res/peg.png");
                        pegs.add(peg);
                    } else if (type.equals("blue_peg_vertical")) {
                        pegImage = new Image("res/vertical-peg.png");
                        BluePeg peg = new BluePeg(pegPosition, pegImage, true, "res/vertical-peg.png");
                        pegs.add(peg);
                    } else if (type.equals("blue_peg_horizontal")) {
                        pegImage = new Image("res/horizontal-peg.png");
                        BluePeg peg = new BluePeg(pegPosition, pegImage, true, "res/horizontal-peg.png");
                        pegs.add(peg);
                    } else if (type.equals("grey_peg")) {
                        pegImage = new Image("res/grey-peg.png");
                        GreyPeg peg = new GreyPeg(pegPosition, pegImage, true, "res/grey-peg.png");
                        pegs.add(peg);
                    } else if (type.equals("grey_peg_vertical")) {
                        pegImage = new Image("res/grey-vertical-peg.png");
                        GreyPeg peg = new GreyPeg(pegPosition, pegImage, true, "res/grey-vertical-peg.png");
                        pegs.add(peg);
                    } else if (type.equals("grey_peg_horizontal")) {
                        pegImage = new Image("res/grey-horizontal-peg.png");
                        GreyPeg peg = new GreyPeg(pegPosition, pegImage, true, "res/grey-horizontal-peg.png");
                        pegs.add(peg);
                    }
                }

                // Instantiate RedPegs
                numRedPegs= (int)(0.2*pegs.size());

                int j = 0;
                while(j < numRedPegs){

                    int redIndex= (int)(Math.random()*pegs.size());


                    if(pegs.get(redIndex) instanceof BluePeg){

                        ((BluePeg) pegs.get(redIndex)).createRedPeg(pegs);
                        j++;
                    }
                }

                // Instantiate GreenPeg
                int k = 0;
                while(k < 1) {
                    int greenIndex = (int) (Math.random() * pegs.size());
                    if (pegs.get(greenIndex) instanceof BluePeg) {

                        ((BluePeg) pegs.get(greenIndex)).createGreenPeg(pegs);
                        k++;
                    }
                }

                GameBoard gameBoard = new GameBoard(pegs, numRedPegs);
                gameBoards[i] = gameBoard;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // Instantiate PowerUp
        powerInitialPosition = new Point(BALL_STARTING_POSITION_X, BALL_STARTING_POSITION_Y);
        powerImage = new Image("res/powerup.png");
        powerUp = new PowerUp(powerInitialPosition, powerImage, false);

        // Instantiate Bucket
        bucketInitialPosition = new Point(BUCKET_STARTING_POSITION_X, BUCKET_STARTING_POSITION_Y);
        bucketImage = new Image("res/bucket.png");
        bucket = new Bucket(bucketInitialPosition, bucketImage, true);
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * The entry point for the program. Creates an instance of ShadowBounce
     */
    public static void main(String[] args) {
        ShadowBounce game = new ShadowBounce();
        game.run();
    }

    /**
     * Performs a state update.
     * If in turn over state and left mouse button pressed, 'shoot' a Ball.
     * If a boundary is reached, bounce off in opposite direction.
     * If Peg is hit, Ball makes action dependent on type of Peg hit
     * If GreenPeg hit spawn extra balls
     * If PowerUp visible on screen, randomly moves across screen
     * If PowerUp hit, spawn FireBall
     * If FireBall hit Peg, destroy radius
     * Bucket moves from on edge to the other
     * If Main Ball leaves bottom of Screen, lose a turn, unless Bucket is hit
     * If all RedPegs destroyed, advance to next board
     * If all boards complete, Player has won
     * If no shots left, Player has lost
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {

        currentBoard = gameBoards[currentBoardIndex];
        numRedPegs = currentBoard.getNumRedPegs();
        Ball mainBall = balls.get(0);

        // If in 'turn over' state, reset Ball on click
        if(mainBall.isTurnOver(balls)){
            if(input.wasPressed(MouseButtons.LEFT)){
                mainBall.reset(ballInitialPosition, input);
            }
        }

        // If balls on screen
        for(Ball ball: balls) {
            if(ball.getVisible()) {

                // If ball reaches edge of window, reverse X velocity
                ball.reachedBoundary(LEFT_BOARDER, RIGHT_BOARDER);

                // If ball goes offscreen, decrement shotsLeft, unless hits bucket
                shotsLeft = ball.offScreen(BOTTOM_BOARDER, shotsLeft, bucket, balls, currentBoard, powerUp);

                // If no more shots left, end
                if (shotsLeft == 0) {
                    System.out.println("GAME OVER!");
                    Window.close();
                }

                // Add gravity to ball velocity
                ball.setBallVelocityY(ball.getBallVelocityY() + GRAVITY_CONSTANT);

                // Update ball position
                ball.move();

                // Check whether the ball collides with a powerUp
                if(powerUp.getVisible()){
                    ball.collisionPowerUp(powerUp, balls);
                }

                // Check whether the ball collides with a peg
                numRedPegs = ball.collisionPeg(currentBoard.getPegs(), numRedPegs, balls);
                currentBoard.setNumRedPegs(numRedPegs);

                // If red pegs destroyed, next board
                if (currentBoard.getNumRedPegs() <= 0) {
                    for (Ball b: balls){
                        b.setVisible(false);
                    }
                    powerUp.setVisible(false);
                    currentBoardIndex++;

                    // If completed all boards, end
                    if (currentBoardIndex == NUM_BOARDS) {
                        System.out.println("CONGRATULATIONS YOU WIN!");
                        Window.close();
                    }

                }

                // Draw ball
                ball.render();
            }
        }
        // ------------------------------------------------------------------------------------------------------------

        // PEGS
        // Draw pegs that have not been hit by the ball yet
        for (Peg peg : currentBoard.getPegs()) {
            if (peg.getVisible()) {
                peg.render();
            }
        }

        // POWER UP
        // If powerUp spawned, update position and render
        if(powerUp.getVisible()){
            powerUp.move();
            powerUp.render();
        }

        // BUCKET
        // Move Bucket
        bucket.move();

        // If bucket reaches edge of screen reverse velocity
        bucket.reachedBoundary(LEFT_BOARDER, RIGHT_BOARDER);

        // Render Bucket
        bucket.render();

        // If ESCAPE is pressed, close the game
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
    }
}
