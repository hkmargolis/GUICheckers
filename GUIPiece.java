package ui;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * The GUIPiece class extends StackPane and handles the pieces in the gui. It defines the piece size, shape and color.
 *
 * @author Hannah
 * @verison 1
 */
public class GUIPiece extends StackPane{

    //Variables
    private PieceType pieceType;
    private double mouseX, mouseY;
    private double originX, originY;

    private final double pieceWidth = 25;
    private final double pieceHeight = 25;
    private final Ellipse piece;

    //constructor

    /**
     * Constructor for GUIPiece class. Once a piece is created is listens to be clicked and dragged to a new location.
     * @param type PieceType (enum --> violet or orange)
     * @param row new game starting row
     * @param col new game starting column
     */
    public GUIPiece(PieceType type, int row, int col) {
        this.pieceType = type;
        movePiece(row, col);
        piece = new Ellipse(pieceWidth, pieceHeight);
        piece.setFill(type == PieceType.VIOLET ? Color.DARKVIOLET : Color.ORANGE);

        getChildren().addAll(piece);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + originX, e.getSceneY() - mouseY + originY);
        });
    }
    public GUIPiece(int row, int col){
        movePiece(row, col);
        piece = new Ellipse(pieceWidth, pieceHeight);
        piece.setFill(Color.GREEN);
        getChildren().addAll(piece);
    }

    //getters and setters

    /**
     * This method returns the pieceType of the current piece.
     * @return pieceType
     */
    public PieceType getPieceType(){
        return pieceType;
    }

    public void setPieceType(PieceType pieceType){
        this.pieceType = pieceType;
    }

    /**
     * This method returns the originX (column number) of the current piece on the board (scene).
     * @return originX
     */
    public double getOriginX(){
        return originX;
    }
    public void setOriginX(double x) {
        originX = x;
    }
    /**
     * This method returns the originY (row number) of the current piece on the board (scene).
     * @return
     */
    public double getOriginY(){
        return originY;
    }
    public void setOriginY(double y){
        originY = y;
    }
    //methods

    /**
     * This method moves the current piece. It updates originX and originY to the new row and col of the new tile.
     * Then it relocates the piece.
     * relocate(row,col) sets the pieces's layoutX and layoutY translation properties
     * @param row the new row
     * @param col the new col
     */
    public void movePiece(int row, int col){
        originX = row * 50; //50 is the checkers gui tile size
        originY = col * 50;
        relocate(originX, originY);

    }

    /**
     * This method stops a piece from completing a move if the move attempted is invalid.
     */
    public void abortMove(){
        relocate(originX, originY);
    }

}


