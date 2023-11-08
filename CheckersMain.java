package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static ui.MoveType.MOVE;
import static ui.PieceType.ORANGE;

//Current Bugs
//The game does not end if players have no more moves. It only ends if one player's pieces have all been captured.
//The player vs computer mode does not work.

/**
 * The CheckersMain class has the main method for this program. It prompts the user to pick a ui in the console and then starts a console game of checkers
 * or launches the gui game of checkers. If the user selects gui, this class creates the app, draws the tiles and pieces and listens for movement.
 */
public class CheckersMain extends Application {

    //variables
    public static final int TILE_SIZE = 50;
    //Note: BOARD_WIDTH = 400, BOARD_HEIGHT = 400, INFO_BOX_WIDTH = 200, INFO_BOX_HEIGHT = 400;
    private final Group tiles = new Group();
    private final Group pieces = new Group();
    private final InfoBox infoBox = new InfoBox(8);
    private InfoBoxButtons infoBoxButtons;
    private final Tile[][] board = new Tile[8][8];
    private String turnStatus;
    private Player violetPlayer;
    private Player orangePlayer;

    /**
     * This method creates the gui. It is composed of the root pane and borderpane with buttons for the user to select a game mode, and the board and pieces.
     * The board made of up of tiles and the pieces are drawn in this method. If a button is clicked the game starts and the first player's pieces are allowed to be moved.
     * @return root Pane
     */
    private Parent createCheckersGUI() {
        Pane root = new Pane();
        //add the board to the root
        root.getChildren().addAll(tiles,pieces,infoBox);
        //add the info box to the root
        infoBoxButtons = infoBox.getInfoBoxButtons();

        //if pvp game mode selected
        infoBoxButtons.getPvpGameButton().setOnAction(e-> {
                //create new players
                violetPlayer = new Player();
                orangePlayer = new Player();
                //remove the buttons
                infoBoxButtons.getPvpGameButton().setVisible(false);
                infoBoxButtons.getPvcGameButton().setVisible(false);
                //tell the user violet is the first player
                infoBox.getInfo().setText("Violet's Turn");
                //set turnStatus to violet
                turnStatus = "Violet";
                //enable violet pieces, disable orange pieces
                piecesDisabled(PieceType.VIOLET, false);
                piecesDisabled(ORANGE, true);

            //for the 8 x 8 checkerboard, draw the tiles on the pane
            Tile tile;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    tile = new Tile((row+col) % 2 == 0, col, row); //sets every other square for violetPlayer
                    board[col][row] = tile;
                    tiles.getChildren().add(tile); //add tile

                    //add pieces to tiles
                    GUIPiece piece = null;
                    if (row < 3 && (row+col) % 2 == 1) {
                        piece = drawPiece(PieceType.VIOLET, col, row);//create violet piece
                    }
                    if (row > 4 && (row+col) % 2 != 0) {
                        piece = drawPiece(ORANGE, col, row);//create orange piece
                    }

                    if (piece != null) {
                        tile.setPiece(piece);//add piece to the tile just created
                        pieces.getChildren().add(piece);//add piece to pieces
                    }
                }
            }
        });

        //if pvc game move selected--note this code repeats because I will be implementing a new class for computer pieces to be added to the board in the pvc game mode
        infoBoxButtons.getPvcGameButton().setOnAction(e -> {
                //create the new players
                violetPlayer = new Player();
                orangePlayer = new Player();
                //remove the game mode buttons
                infoBoxButtons.getPvpGameButton().setVisible(false);
                infoBoxButtons.getPvcGameButton().setVisible(false);
                //add the button that is used to generate an ai move **this is the only line that is different from the pvp game
                infoBoxButtons.getComputerTurnButton().setVisible(true);
                //tell the player to go first as violet
                infoBox.getInfo().setText("Violet's Turn");
                //set turn status to violet
                turnStatus = "Violet";
                //enable violet pieces, disable orange pieces
                piecesDisabled(PieceType.VIOLET, false);
                piecesDisabled(ORANGE, true);

                //draw the checkerboard tiles
                Tile tile;
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 8; col++) {
                    tile = new Tile((row+col) % 2 == 0, col, row); //sets every other square for violetPlayer
                    board[col][row] = tile;
                    tiles.getChildren().add(tile); //add tile

                    //add pieces to tiles
                    GUIPiece piece = null;
                    if (row < 3 && (row+col) % 2 == 1) {
                        piece = drawPiece(PieceType.VIOLET, col, row);//create violet piece
                    }
                    if (row > 4 && (row+col) % 2 != 0) {
                        piece = drawPiece(ORANGE, col, row);//create orange piece
                    }

                    if (piece != null) {
                        tile.setPiece(piece);//add piece to the tile just created
                        pieces.getChildren().add(piece);//add piece to pieces
                    }
                }
            }
        });

        return root;
    }

    //methods

    /**
     * This method draws a piece on the board.
     * @param pieceType enum VIOLET or ORANGE
     * @param row starting row for newgame
     * @param col starting col for newgame
     * @return piece GUIPiece
     */
    private GUIPiece drawPiece(PieceType pieceType, int row, int col) {
        GUIPiece piece = new GUIPiece(pieceType, row, col);
        //set piece to listen for mouse drag and release to move them from human player
        piece.setOnMouseReleased(e -> {
            int destinationX = draw(piece.getLayoutX());
            int destinationY = draw(piece.getLayoutY());
            MoveHandler handler = getMoveStatus(piece, destinationX, destinationY);
            int originX = draw(piece.getOriginX());
            int originY = draw(piece.getOriginY());

            //check to see if the user dragged the piece to a valid move destination
            switch (handler.getMoveType()) {
                case NONE -> {
                    piece.abortMove();
                    break;
                }
                case MOVE -> {
                    piece.movePiece(destinationX, destinationY);
                    board[originX][originY].setPiece(null);
                    board[destinationX][destinationY].setPiece(piece);
                    infoBoxButtons.getOrangeComputerPlayerLabel().setText("");
                    handleTurn(0);
                    break;
                }
                case CAPTURE -> {
                    piece.movePiece(destinationX, destinationY);
                    board[originX][originY].setPiece(null);
                    board[destinationX][destinationY].setPiece(piece);
                    GUIPiece capturedPiece = handler.getPiece();
                    int capturedPieceOriginX = draw(capturedPiece.getOriginX());
                    int capturedPieceOriginY = draw(capturedPiece.getOriginY());
                    board[capturedPieceOriginX][capturedPieceOriginY].setPiece(null);
                    pieces.getChildren().remove(capturedPiece);
                    handleTurn(1);

                    String winStatus = checkWinStatus();
                    if (winStatus.equals("Violet")) {
                        infoBox.getInfo().setText("Violet wins!");
                    } else if (winStatus.equals("Orange")) {
                        infoBox.getInfo().setText("Orange wins!");
                    }
                    infoBoxButtons.getOrangeComputerPlayerLabel().setText("");
                    break;
                }
            }
        });
        //if the game mode is pvc, the user must click the generate computer move button and manually drag the piece to the given coords
        infoBoxButtons.getComputerTurnButton().setOnAction(e -> {
            int[] computerCoords = null;
            while(computerCoords == null){
                computerCoords = generateRandomCoords();
            }

                int originCol = computerCoords[0];
                int originRow = computerCoords[1];
                int destinationCol = computerCoords[2];
                int destinationRow = computerCoords[3];
                //displays the computer coords for the user
                infoBoxButtons.getOrangeComputerPlayerLabel().setText("Move piece at col: " + originCol + " row: " + originRow +
                        "\nto col:" + destinationCol + " row: " + destinationRow);

        });

        return piece;

    }

    /**
     * This methods generates random coordinates for a valid MOVE (MoveType) only. It does not yet generate a valid CAPTURE (MoveType).
     * @return coords int[]{origincol, originrow, destinationcol, destiantionrow}
     */
    public int[] generateRandomCoords() {
        int originCol = -1;
        int originRow = -1;
        GUIPiece computerPiece = null;
        boolean foundPiece = false;
        //looks until it finds an orange piece on the board
        while (!foundPiece) {
            originCol = generateRandomNum();
            originRow = generateRandomNum();

            if (board[originCol][originRow].containsPiece()) {
                if (board[originCol][originRow].getPiece().getPieceType().equals(ORANGE)) {
                    foundPiece = true;
                    computerPiece = new GUIPiece(ORANGE, originRow, originCol);
                    //System.out.println("Piece origin --> col:" + originCol + " row: " + originRow);
                }
            }
        }
        //checks to see if piece can be moved to the right or left without going OB or moving onto another piece
        int destinationColLeft = originCol - 1;
        int destinationColRight = originCol + 1;
        int finalDestinationRow = originRow - 1;
        int finalDestinationCol = -1;

        if (finalDestinationRow >= 0) {

            if (destinationColLeft >= 0 && destinationColLeft < 8 && !board[destinationColLeft][finalDestinationRow].containsPiece()) {
                finalDestinationCol = destinationColLeft;
                return new int[]{originCol, originRow, finalDestinationCol, finalDestinationRow};
            } else if (destinationColRight >= 0 && destinationColRight < 8 && !board[destinationColRight][finalDestinationRow].containsPiece()) {
                finalDestinationCol = destinationColRight;
                return new int[]{originCol, originRow, finalDestinationCol, finalDestinationRow};
            }
        }
        //if the piece can't be moved, returns null
        return null;
    }

    /**
     * Thie method generates a random number between 0 (inclusive) and 8 (exclusive).
     * @return randNum int
     */
    public int generateRandomNum() {
        Random random = new Random();
        return random.nextInt(8);
    }

    /**
     * Thie methods toggles the pieces ability to be moved by a player.
     * @param pieceType
     * @param moveState (true if piece is disabled)
     */
    public void piecesDisabled(PieceType pieceType, boolean moveState) {
        //when state == true, pieces cannot be moved
        //when state == false, pieces can be moved
            for (int i = 0; i < pieces.getChildren().size(); i++) {
                Node tempNode = pieces.getChildren().get(i);
                    GUIPiece piece = (GUIPiece) tempNode;
                    if (piece.getPieceType() == pieceType) {
                        piece.setDisable(moveState);
                    }
            }
    }

    /**
     * This method gets the move status of a piece. It checks for a valid move and returns and enum MoveType MOVE, CAPTURE, or NONE.
     * @param piece GUIPiece
     * @param destinationX destination column
     * @param destinationY destination row
     * @return handler MoveHandler
     */
    private MoveHandler getMoveStatus(GUIPiece piece, int destinationX, int destinationY){
        int originX = draw(piece.getOriginX());
        int originY = draw(piece.getOriginY());
        //if player tries to move more than 2 spaces in any direction, return NONE because this move is invalid
        if(Math.abs(destinationX - originX) > 2 || Math.abs(destinationY - originY) > 2){
            return new MoveHandler(MoveType.NONE);
        }
        //if the destination tile contains a piece, return NONE pieces can only move to empty squares
        if(board[destinationX][destinationY].containsPiece()){
            return new MoveHandler(MoveType.NONE);
        }
        //if the destination cell is a white tile, return NONE pieces can only move on black tiles
        if((destinationX + destinationY) % 2 == 0){
            return new MoveHandler(MoveType.NONE);
        }
        //if the destination tile is adjacent to the origin tile && piece is being moved forward
        if(Math.abs(destinationX - originX) == 1 && destinationY - originY == piece.getPieceType().moveDirection){
            if(destinationY < 0 || destinationY > 7){
                return new MoveHandler(MoveType.NONE);
            }
            return new MoveHandler(MOVE);
        }

        //if the destination tile is 2 tiles away from the origin tile && the piece is being moved forward
        if(Math.abs(destinationX - originX) == 2 && destinationY - originY == piece.getPieceType().moveDirection * 2){
            //get capture tile coords
            int captureXCoord = originX + (destinationX - originX) / 2;
            int captureYCoord = originY + (destinationY - originY) / 2;
            //get piece to be captured
            GUIPiece capturedPiece = board[captureXCoord][captureYCoord].getPiece();
            PieceType capturedPieceType = null;
            if(capturedPiece != null){
                capturedPieceType = capturedPiece.getPieceType(); //get the captured piece type
            }
            PieceType currentPlayerPieceType = piece.getPieceType(); //get the current piece type
            //if there is a piece at the capture coords && the piece to be captured is an opponent's piece
            if(board[captureXCoord][captureYCoord].containsPiece() && capturedPieceType != currentPlayerPieceType) {
                return new MoveHandler(MoveType.CAPTURE, board[captureXCoord][captureYCoord].getPiece());
            }
        }return new MoveHandler(MoveType.NONE); //all other move types invalid
    }

    /**
     * This method switches the turnstatus between ORANGE and VIOLET and also updates the display in the Infobox so the player knows whose turn it is.
     * @param numOfCaptures takes in 0 or 1 and keeps track of how many pieces each player has captured which later helps determine win status.
     */
    public void handleTurn(int numOfCaptures){
        switch(turnStatus){
            case "Violet" -> {
                piecesDisabled(PieceType.VIOLET, true);
                piecesDisabled(ORANGE, false);
                if(numOfCaptures == 1){
                    violetPlayer.incrementCaptureCount(numOfCaptures);
                    infoBoxButtons.setCaptures(violetPlayer.getCapturedPieces(), turnStatus);
                }
                turnStatus = "Orange";
                infoBox.getInfo().setText("Orange's Turn"); //update InfoBox
                break;
            }
            case "Orange" -> {
                piecesDisabled(PieceType.VIOLET, false);
                piecesDisabled(ORANGE, true);
                if(numOfCaptures == 1){
                    orangePlayer.incrementCaptureCount(numOfCaptures);
                    infoBoxButtons.setCaptures(orangePlayer.getCapturedPieces(), turnStatus);
                }
                turnStatus = "Violet";
                infoBox.getInfo().setText("Violet's Turn"); //update InfoBox
                break;
            }
            default -> {

            }
        }
    }

    /**
     * This method checks the win status and returns the winning player's color or an empty string if no winner yet. Currently the only way to win is to capture all of the opponents pieces.
     * @return winner String
     */
    public String checkWinStatus(){
        if(orangePlayer.getCapturedPieces() == 12){
            return "Orange";
        }
        else if(violetPlayer.getCapturedPieces() == 12){
            return "Violet";
        }
        //to do: orange wins if violet has no more possible moves
        //to do: violet wins if orange has no more possible moves
        return "";
    }

    /**
     * This is a helper method that draws the pieces on the board in the correct locations according to the tile dimensions.
     * @param pixel
     * @return
     */
    public int draw(double pixel){
        //System.out.println("drawing pixel");
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * This method starts the gui by creating a scene.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createCheckersGUI());
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The main method for this program starts  in the console, asks the user to choose a ui and then starts a console game of checkers or a gui game of checkers.
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Checkers\nPlease select a Game UI.\n1.GUI\n2.Console\nEnter 1 or 2 to make your selection.");
        int ui = in.nextInt();
        boolean start = false;
        while (!start) {
            if (ui == 1) {
                System.out.println("Loading GUI game...");
                start = true;
                in.close();
                launch();
            } else if (ui == 2) {
                System.out.println("Loading console game...");
                start = true;
                CheckersTextConsole console = new CheckersTextConsole();
                console.start();
            } else {
                System.out.println("Invalid selection. Please enter 1 or 2.");
                ui = in.nextInt();
            }
        }
    }

}
