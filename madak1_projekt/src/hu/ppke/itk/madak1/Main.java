package hu.ppke.itk.madak1;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    private static boolean CONSOLE_MODE;

    /**
     * Check the program arguments
     *  --> If args contain '-nogui' or '-console' then turn the console mode on
     *  --> Else the program will start with GUI
     * @param args Program arguments
     */
    public static void main(String[] args){
        Main.CONSOLE_MODE = Arrays.asList(args).contains("-nogui") || Arrays.asList(args).contains("-console");
        launch(args);
    }

    /**
     * Start
     *  - This function will start the program in console or GUI mode
     * @param stage The Stage
     * @throws Exception The exception what the program could throw
     */
    @Override
    public void start(Stage stage) throws Exception {
        GameMaster gameMaster;
        if(Main.CONSOLE_MODE) gameMaster = new ConsoleMaster();
        else gameMaster = new GuiMaster(stage);
        gameMaster.gameMain();
    }
}
