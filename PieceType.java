package ui;

/**
 * The possible PieceTypes for checkers.
 */
public enum PieceType {
    VIOLET(1), ORANGE(-1), COMPUTER(-1);
    //variables
    final int moveDirection;
    //constructor

    /**
     * This method takes a MoveDirection of either 1 or -1 and sets this object's move direction.
     * 1 if player is violet, -1 if player is orange
     * @param moveDirection
     */
    PieceType(int moveDirection){
        this.moveDirection = moveDirection;
    }
}
