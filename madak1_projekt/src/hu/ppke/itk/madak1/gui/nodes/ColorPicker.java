package hu.ppke.itk.madak1.gui.nodes;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ColorPicker extends Pane {

    private boolean isHided;
    private Color actColor;
    private final double radius;
    private final double miniRadius;
    private final Circle mainCircle;
    private final Circle[] circles;

    /**
     * Color picker constructor
     *  - Set main and little circles
     * @param radius The main circle size
     * @param colors The colors
     * @param startColor The starter color
     */
    public ColorPicker(double radius, Color[] colors, Color startColor){
        this.setMaxSize(radius * 2.0D, radius * 2.0D * (colors.length + 1));

        this.isHided = true;
        this.actColor = startColor;
        this.radius = radius;
        this.miniRadius = radius * 0.75D;

        this.mainCircle = new Circle(radius);
        this.mainCircle.setCenterX(radius);
        this.mainCircle.setCenterY(radius);
        this.mainCircle.setFill(startColor);
        this.mainCircle.setStroke(Color.BLACK);
        this.mainCircle.setStrokeWidth(radius * 0.1D);
        this.mainCircle.setEffect(new DropShadow(15.0D, 0.0D, 0.0D, Color.BLACK));
        this.mainCircle.setOnMouseClicked(mouseEvent -> {
            if(this.isHided) this.showColors();
            else this.hideColors();
        });

        this.circles = new Circle[colors.length];
        for (int i = 0; i < this.circles.length; i++) {
            Circle circle = new Circle(this.miniRadius);
            circle.setTranslateX(this.radius);
            circle.setTranslateY(this.radius);
            circle.setFill(colors[i]);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(this.miniRadius * 0.1D);
            circle.setEffect(new DropShadow(10.0D, 0.0D, 0.0D, Color.BLACK));
            final int tmpColorIndex = i;
            circle.setOnMouseClicked(mouseEvent ->{
                this.mainCircle.setFill(colors[tmpColorIndex]);
                this.actColor = colors[tmpColorIndex];
                this.hideColors();
            });
            this.circles[i] = circle;
            this.getChildren().add(circle);
        }

        this.getChildren().add(this.mainCircle);

    }

    /**
     * Show the color options
     *  - Show the colors what we can select
     */
    public void showColors(){
        for (int i = 0; i < this.circles.length; i++) {
            TranslateTransition translateAnimation = new TranslateTransition(Duration.millis(150.0D));
            translateAnimation.setInterpolator(Interpolator.LINEAR);
            translateAnimation.setNode(this.circles[i]);
            translateAnimation.setToY(i * this.miniRadius * 2.5D + this.radius * 3.5D);
            translateAnimation.play();
        }
        this.isHided = false;
    }

    /**
     * Hide the color options
     *  - Hide the color selection
     */
    public void hideColors(){
        for (Circle circle : this.circles) {
            TranslateTransition translateAnimation = new TranslateTransition(Duration.millis(150.0D));
            translateAnimation.setInterpolator(Interpolator.LINEAR);
            translateAnimation.setNode(circle);
            translateAnimation.setToY(this.radius);
            translateAnimation.play();
        }
        this.isHided = true;
    }

    /**
     * Getter for actual color
     *  - Get the selected color
     * @return The actual color
     */
    public Color getActColor() {
        return actColor;
    }

}
