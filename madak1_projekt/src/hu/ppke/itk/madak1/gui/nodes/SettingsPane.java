package hu.ppke.itk.madak1.gui.nodes;

import javafx.scene.layout.*;

public class SettingsPane extends StackPane {

    /**
     * Setting pane constructor
     *  - Set style to buttons
     * @param buttons The buttons
     */
    public SettingsPane(OptionButton[] buttons){
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(0,0);
        gridPane.setVgap(15D);
        gridPane.setHgap(15D);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 5; ++j) {
                gridPane.add(buttons[((i * 5) + j)], j, i);
            }
        }
        this.getChildren().add(gridPane);
    }
}
