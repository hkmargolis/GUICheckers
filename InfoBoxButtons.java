package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class creates a borderpane with buttons for the user to select a game mode and generate a computer move.
 * The constructor creates all three buttons, the computer label that displays the computer move and keeps track of the captures by each piece.
 */
public class InfoBoxButtons extends BorderPane {
    private Button pvpGameButton; //player vs player game
    private Button pvcGameButton; //player vs computer game
    private Button computerTurnButton;

    private Label orangeComputerPlayerLabel;
    public int violetCaptureCount;
    public int orangeCaptureCount;

    //constructor
    public InfoBoxButtons() {
        setPrefSize(250,200);
        setMargin(this, new Insets(5, 5, 5, 5));
        setStyle("-fx-background-color: #333333");

        //add pvp button
        pvpGameButton = new Button();
        pvpGameButton.setPrefSize(150,100);
        pvpGameButton.setText("Player vs Player");
        pvpGameButton.setTextFill(Color.WHITE);
        pvpGameButton.setFont(new Font("Arial", 14));
        pvpGameButton.setStyle("-fx-background-color: black; -fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        setMargin(pvpGameButton, new Insets(0,0,12,0));
        pvpGameButton.setAlignment(Pos.CENTER);
        setRight(pvpGameButton);

        //add pcv button
        pvcGameButton = new Button();
        pvcGameButton.setPrefSize(150,100);
        pvcGameButton.setText("Player vs Computer");
        pvcGameButton.setTextFill(Color.WHITE);
        pvcGameButton.setFont(new Font("Arial",14));
        pvcGameButton.setStyle("-fx-background-color: black; -fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        setMargin(pvcGameButton, new Insets(0,0,12,0));
        pvcGameButton.setAlignment(Pos.CENTER);
        setLeft(pvcGameButton);

        //add generate computer move button
        computerTurnButton = new Button();
        computerTurnButton.setPrefSize(200,200);
        computerTurnButton.setText("Generate AI move.");
        computerTurnButton.setTextFill(Color.WHITE);
        computerTurnButton.setFont(new Font("Arial",14));
        computerTurnButton.setStyle("-fx-background-color: black; -fx-background-color: black; -fx-background-radius: 15px; -fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 10 10 10 10;");
        setMargin(computerTurnButton,new Insets(0,0,12,0));
        computerTurnButton.setAlignment(Pos.CENTER);
        setBottom(computerTurnButton);
        computerTurnButton.setVisible(false);

        //add label that displays computer move
        orangeComputerPlayerLabel = new Label("");
        orangeComputerPlayerLabel.setTextFill(Color.ORANGE);
        orangeComputerPlayerLabel.setPrefSize(200,150);
        orangeComputerPlayerLabel.setAlignment(Pos.CENTER);
        orangeComputerPlayerLabel.setFont(new Font("Arial", 14));
        setMargin(orangeComputerPlayerLabel, new Insets(0,0,10,14));
        setTop(orangeComputerPlayerLabel);

    }

    // methods

    /**
     * This method sets the captures for the pieces. This needs to be reorganized and moved to a class that makes more sense.
     * @param totalCaptures 0 or 1
     * @param player "Violet" or "Orange"
     */
    public void setCaptures(int totalCaptures, String player){
        if(player.equals("Violet")){
            this.violetCaptureCount = totalCaptures;
        }
        else{
            this.orangeCaptureCount = totalCaptures;
        }
    }

    /**
     * This method gets the computerTurnButton.
     * @return computerTurnButton
     */
   public Button getComputerTurnButton(){
        return computerTurnButton;
    }

    /**
     * This methods get the orangeComputerPlayerLabel.
     * @return orangeComputerPlayerLabel
     */
    public Label getOrangeComputerPlayerLabel(){
        return orangeComputerPlayerLabel;
    }

    /**
     * This method gets the player vs player button.
     * @return pvpGameButton
     */
    public Button getPvpGameButton(){
        return pvpGameButton;
    }

    /**
     * This method get the player vs computer game button
     * @return pvcGameButton
     */
    public Button getPvcGameButton(){
        return pvcGameButton;
    }
}
