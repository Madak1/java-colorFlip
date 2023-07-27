package hu.ppke.itk.madak1.gui.nodes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuPane extends VBox {

    /**
     * Menu pane constructor
     *  - Set style to title and buttons
     * @param title The title of the menu
     * @param buttons The buttons
     * @param titleSize The title size
     * @param space The space between buttons
     */
    public MenuPane(String title, OptionButton[] buttons, double titleSize, double space){

        for (OptionButton button : buttons) button.setMaxWidth(button.getWidth());

        Label label = new Label(title);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", FontWeight.BOLD, titleSize));
        label.setTextFill(Color.WHITESMOKE);
        label.setEffect(new DropShadow(15.0D, 4.0D, 4.0D, Color.BLACK));

        this.setSpacing(space);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(label);
        this.getChildren().addAll(buttons);
    }

}
