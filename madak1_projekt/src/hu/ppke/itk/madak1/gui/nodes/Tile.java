package hu.ppke.itk.madak1.gui.nodes;

import hu.ppke.itk.madak1.GuiMaster;
import javafx.animation.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Tile extends StackPane {

    private boolean isPrimary;

    private final Rectangle outerRec;
    private final Rectangle innerRec;

    private final RotateTransition rotateAnimation;
    private final ScaleTransition scaleAnimation;

    /**
     * Tile constructor
     *  - Create outer rectangle
     *  - Create inner rectangle
     *  - Set animations
     * @param size Tile size
     * @param isPrimary Primary or secondary color at start
     */
    public Tile(double size, boolean isPrimary){
        this.isPrimary = isPrimary;
        double innerRecSize = size * 0.9D;

        this.outerRec = new Rectangle(size, size);
        this.outerRec.setFill(Color.BLACK);
        this.outerRec.setArcHeight(size * 0.15D);
        this.outerRec.setArcWidth(size * 0.15D);

        this.innerRec = new Rectangle(innerRecSize, innerRecSize);
        this.innerRec.setFill(isPrimary ? GuiMaster.PRIMARY_COLOR : GuiMaster.SECONDARY_COLOR);
        this.innerRec.setArcHeight(innerRecSize * 0.15D);
        this.innerRec.setArcWidth(innerRecSize * 0.15D);

        this.getChildren().addAll(this.outerRec, this.innerRec);

        this.rotateAnimation = new RotateTransition(Duration.millis(150.0D), this.innerRec);
        this.rotateAnimation.setFromAngle(0.0D);
        this.rotateAnimation.setToAngle(90.0D);

        this.scaleAnimation = new ScaleTransition(Duration.millis(150.0D), this.innerRec);
        this.scaleAnimation.setFromX(1.0D);
        this.scaleAnimation.setFromY(1.0D);
        this.scaleAnimation.setToX(0.8D);
        this.scaleAnimation.setToY(0.8D);
        this.scaleAnimation.setAutoReverse(true);
        this.scaleAnimation.setCycleCount(2);
    }

    /**
     * Color flip
     *  - Play animation
     *  - Change color
     */
    public void colorFlip(){
        this.rotateAnimation.play();
        this.scaleAnimation.play();
        this.isPrimary = !this.isPrimary;
        this.innerRec.setFill(this.isPrimary ? GuiMaster.PRIMARY_COLOR : GuiMaster.SECONDARY_COLOR);
    }

    /**
     * Set the stroke
     *  - Set green stroke if the tile in the solution
     * @param toOn Set on or off
     */
    public void setHelpStroke(boolean toOn) {
        if(toOn) this.outerRec.setFill(Color.LIME);
        else this.outerRec.setFill(Color.BLACK);
    }

    /**
     * Update tile color
     *  - Update the color of the tile
     */
    public void updateColor() {
        this.innerRec.setFill(this.isPrimary ? GuiMaster.PRIMARY_COLOR : GuiMaster.SECONDARY_COLOR);
    }
}
