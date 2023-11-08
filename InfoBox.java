package ui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The InfoBox class extends BorderPane. It displays "New Game", "Game Over", the players turn status, the computer generated moves and the generate move button.
 */
public class InfoBox extends BorderPane {
    //variables
    private InfoBoxButtons infoBoxButtons;
    private Label info;

    //constructor

    /**
     * The constructor creates the info box, displays "New Game", sets the size, color and adds an InfoBoxButton object.
     * @param xcoord
     */
    public InfoBox(int xcoord){
        setPrefSize(100,400);
        relocate(xcoord*50,0);
        setStyle("-fx-background-color: #333333");

        //initial info
        info = new Label("New Game");
        info.setPrefSize(200,35);
        info.setTextFill(Color.WHITE);
        info.setFont(new Font("Arial", 20));
        info.setAlignment(Pos.CENTER);
        setMargin(info, new Insets(10,0,0,0));
        setAlignment(info,Pos.CENTER);
        setTop(info);

        infoBoxButtons = new InfoBoxButtons();
        setAlignment(infoBoxButtons,Pos.CENTER);
        setCenter(infoBoxButtons);
    }

    //methods

    /**
     * This method gets the current info such as "New Game"
     * @return info a Label
     */
    public Label getInfo(){
        return info;
    }

    /**
     * This method get the InfoBoxButtons object
     * @return infoBoxButtons Borderpane
     */
    public InfoBoxButtons getInfoBoxButtons(){
        return infoBoxButtons;
    }

}
