package ui;

/**
 * This class stores and updates the number of pieces captured by each player.
 */
public class Player {
    //variables
    private int capturedPieces;

    //constructor

    /**
     * Constructor sets this player's pieces to 0 for start of new game
     */
    public Player(){
        this.capturedPieces = 0;
    }

    //methods

    /**
     * This method takes in the number of captures during a move (0 or 1) and adds it to the total number of captures
     * @param numOfCaptures
     */
    public void incrementCaptureCount(int numOfCaptures){
        capturedPieces += numOfCaptures;
    }

    /**
     * This method returns the number of captured pieces for this player.
     * @return number
     */
    public int getCapturedPieces(){
        return capturedPieces;
    }
}
