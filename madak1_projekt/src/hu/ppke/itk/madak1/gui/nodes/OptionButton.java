package hu.ppke.itk.madak1.gui.nodes;

import hu.ppke.itk.madak1.GuiMaster;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class OptionButton extends StackPane {

    private final Rectangle innerRec;
    private final Label label;
    private final String text, hint;
    private final double textSize, hintSize;

    /**
     * Option button Constructor
     *  - Set outer rectangle
     *  - Set inner rectangle
     *  - Set the text and hint
     *  - Set actions
     * @param width Button width
     * @param height Button height
     * @param text Button text
     * @param textSize Text size
     * @param hint Hint for the action of the button
     * @param hintSize Hint size
     */
    public OptionButton(double width, double height, String text, double textSize, String hint, double hintSize){

        Rectangle outerRec = new Rectangle(width, height, Color.BLACK);
        outerRec.setArcHeight(width * 0.06D);
        outerRec.setArcWidth(width * 0.06D);

        this.innerRec = new Rectangle(width - height * 0.1D, height * 0.9D, GuiMaster.PRIMARY_COLOR);
        this.innerRec.setArcHeight(width * 0.05D);
        this.innerRec.setArcWidth(width * 0.05D);

        this.text = text.toUpperCase();
        this.hint = hint.toUpperCase();
        this.textSize = textSize;
        this.hintSize = hintSize;

        this.label = new Label(this.text);
        this.label.setFont(Font.font("Arial", FontWeight.BOLD, this.textSize));
        this.label.setTextFill(Color.BLACK);

        this.getChildren().addAll(outerRec, this.innerRec, this.label);

        this.setOnMouseEntered(mouseEvent -> {
            this.label.setText(this.hint);
            this.label.setFont(Font.font("Arial", FontWeight.BOLD, this.hintSize));
            this.fillAnimation(this.innerRec, GuiMaster.SECONDARY_COLOR);
            this.scaleAnimation(this.innerRec);
            this.scaleAnimation(this.label);
        });

        this.setOnMouseExited(mouseEvent -> {
            this.label.setText(this.text);
            this.label.setFont(Font.font("Arial", FontWeight.BOLD, this.textSize));
            this.fillAnimation(this.innerRec, GuiMaster.PRIMARY_COLOR);
        });
    }

    /**
     * Scale animation setup
     *  - Setup scale animation and play it
     * @param node Animation target
     */
    private void scaleAnimation(Node node){
        ScaleTransition scale = new ScaleTransition(Duration.millis(150.0D), node);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(0.9);
        scale.setToY(0.9);
        scale.setAutoReverse(true);
        scale.setCycleCount(2);
        scale.play();
    }

    /**
     * Fill animation setup
     *  - Setup fill animation and play it
     * @param shape Animation target
     * @param to To color
     */
    private void fillAnimation(Shape shape, Color to){
        FillTransition fill = new FillTransition(Duration.millis(150.0D), shape);
        fill.setInterpolator(Interpolator.LINEAR);
        fill.setToValue(to);
        fill.play();
    }

    /**
     * Update button color
     *  - Update the inner rectangle of the button
     */
    public void updateColors(){
        this.innerRec.setFill(GuiMaster.PRIMARY_COLOR);
    }

}
