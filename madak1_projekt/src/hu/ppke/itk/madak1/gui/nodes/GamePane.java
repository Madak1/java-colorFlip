package hu.ppke.itk.madak1.gui.nodes;

import hu.ppke.itk.madak1.GuiMaster;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class GamePane extends BorderPane {

    private final TimerLabel timer;
    private final Board board;
    private final ColorPicker primaryPicker;
    private final ColorPicker secondaryPicker;
    private final OptionButton[] buttons;

    /**
     * Game pane constructor
     *  - Init some variables
     * @param timer The timer
     * @param board The board
     * @param primPkr Primary color picker
     * @param secPkr Secondary color picker
     * @param buttons The buttons
     */
    public GamePane(TimerLabel timer, Board board, ColorPicker primPkr, ColorPicker secPkr, OptionButton[] buttons){
        this.timer = timer;
        this.board = board;
        this.primaryPicker = primPkr;
        this.secondaryPicker = secPkr;
        this.buttons = buttons;
    }

    /**
     * Setup for Game pane
     *  - Add buttons, pickers, board, and timer to game pane
     */
    public void setup(){

        HBox buttonsPane = new HBox(this.buttons[1], this.buttons[2], this.buttons[3], this.buttons[4]);
        buttonsPane.setSpacing((GuiMaster.MIN_XX / this.buttons.length) / (this.buttons.length - 2) / 2D );
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setMaxHeight(this.buttons[0].getHeight());

        StackPane top = new StackPane(this.timer);
        StackPane left = new StackPane(this.primaryPicker);
        StackPane center = new StackPane(this.board);
        StackPane right = new StackPane(this.secondaryPicker);
        StackPane bottom = new StackPane(buttonsPane);

        top.setMinHeight((GuiMaster.MIN_YY - this.board.getMaxHeight()) * 0.3D);
        left.setMinWidth((GuiMaster.MIN_XX - this.board.getMaxWidth()) * 0.4D);
        right.setMinWidth((GuiMaster.MIN_XX - this.board.getMaxWidth()) * 0.4D);
        bottom.setMinHeight((GuiMaster.MIN_YY - this.board.getMaxHeight()) * 0.3D);

        ///top.setBackground(new Background(new BackgroundFill(Color.RED.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
        ///center.setBackground(new Background(new BackgroundFill(Color.GREEN.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
        ///left.setBackground(new Background(new BackgroundFill(Color.BLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
        ///right.setBackground(new Background(new BackgroundFill(Color.BLUE.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
        ///bottom.setBackground(new Background(new BackgroundFill(Color.RED.darker(), CornerRadii.EMPTY, Insets.EMPTY)));

        this.setTop(top);
        this.setLeft(left);
        this.setRight(right);
        this.setBottom(bottom);
        this.setCenter(center);
    }
}
