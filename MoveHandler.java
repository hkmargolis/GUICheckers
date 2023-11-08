package ui;

/**
 * This class is used when checking if a piece can be moved legally. It holds the piece and the MoveType for the piece.
 */
public class MoveHandler {
    //variables
    private MoveType moveType;
    private GUIPiece piece;

    //constructor for GUIPiece

    /**
     * This method takes in a MoveType enum (MOVE, CAPTURE, NONE) and a piece and creates a MoveHandler object.
     * @param moveType
     * @param piece
     */
    public MoveHandler(MoveType moveType, GUIPiece piece){
        this.moveType = moveType;
        this.piece = piece;
    }
    //constructor for GUIComputerPiece

    //methods

    /**
     * This method takes in a moveType only and creates a MoveHandler object.
     * @param moveType
     */
    public MoveHandler(MoveType moveType){
        this(moveType, (GUIPiece) null);
    }

    /**
     * This method gets the MoveType for this object.
     * @return moveType enum MOVE, CAPTURE, NONE
     */
    public MoveType getMoveType(){
        return this.moveType;
    }

    /**
     * This method returns the piece for this object.
     * @return piece GUIPiece
     */
    public GUIPiece getPiece(){
        return this.piece;
    }
}
