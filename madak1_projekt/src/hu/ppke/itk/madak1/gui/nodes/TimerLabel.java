package hu.ppke.itk.madak1.gui.nodes;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimerLabel extends Label{

    private final AnimationTimer animationTimer;
    private long startTime;
    private long elapsedSec;

    /**
     * Timer label Constructor
     *  - Set the style
     *  - Set the elapsed time
     * @param clockSize Size of the time label
     */
    public TimerLabel(double clockSize){

        this.setAlignment(Pos.CENTER);
        this.setFont(Font.font("Arial", FontWeight.BOLD, clockSize));
        this.setTextFill(Color.WHITESMOKE);
        this.setEffect(new DropShadow(20.0D, 0.0D, 0.0D, Color.BLACK));

        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                TimerLabel.this.elapsedSec = (System.currentTimeMillis() - TimerLabel.this.startTime)/1000L;

                int hours = (int)(((elapsedSec/60D)/60D) % 60D);
                int minutes = (int)((elapsedSec/60D) % 60D);
                int seconds = (int)(elapsedSec % 60D);

                String sHours = String.valueOf(hours);
                if(hours < 10) sHours = "0" + hours;
                String sMinutes = String.valueOf(minutes);
                if(minutes < 10) sMinutes = "0" + minutes;
                String sSeconds = String.valueOf(seconds);
                if(seconds < 10) sSeconds = "0" + seconds;

                TimerLabel.this.setText(sHours + ":" + sMinutes + ":" + sSeconds);
            }
        };

    }

    /**
     * Start the timer
     *  - Start the timer and set the start time if necessary
     * @param bonus Add time to start
     */
    public void startTimer(long bonus){
        this.startTime = System.currentTimeMillis() - bonus * 1000;
        this.animationTimer.start();
    }

    /**
     * Stop the timer
     *  - It stops the timer
     */
    public void stopTimer(){
        this.animationTimer.stop();
    }

    /**
     * Getter for elapsed time
     *  - Get the elapsed time
     * @return Elapsed time
     */
    public long getElapsedTime(){
        return this.elapsedSec;
    }

}