package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The Tile class extends Rectangle and stores the dimensions and color options (gray or white) for a tile on the checkerboard.
 */
public class Tile extends Rectangle{
    //variables
    private GUIPiece piece;
    //contructor

    /**
     * The constructor sets the size, color and location of the squares on the checkerboard.
     * @param violet
     * @param xcoord
     * @param ycoord
     */
    public Tile(boolean violet, int xcoord, int ycoord){
        setWidth(50);
        setHeight(50);
        relocate(50*xcoord, 50*ycoord);
        setFill(violet ? Color.GRAY : Color.WHITE);
    }
    //getters and setters

    /**
     * This method sets this tile's piece.
     * @param piece
     */
    public void setPiece(GUIPiece piece){
        this.piece = piece;
    }

    /**
     * This method gets this tile's piece
     * @return piece
     */
    public GUIPiece getPiece(){
        return piece;
    }

    /**
     * This method returns true if the tile contains a piece.
     * @return containsPiece
     */
    public boolean containsPiece(){
        return piece != null;
    }
}
