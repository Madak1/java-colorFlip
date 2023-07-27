package hu.ppke.itk.madak1;

import java.io.IOException;

public abstract class GameMaster{
    protected enum Phase {MENU, START, NEW_GAME, LOAD_GAME, GAME, EXIT}

    protected Phase phase;
    protected GameLogic logic;

    protected String title;
    protected String[] options;
    protected String[] description;

    /**
     * Phase changer
     *  - Set the phase
     *  - Change the phase texts
     * @param phase Change phase to that phase
     */
    protected void phaseChanger(Phase phase) {
        this.phase = phase;
        this.hintChanger();
    }

    /**
     * Hint changer
     *  - This func will set the texts for the actual phase
     */
    protected void hintChanger(){
        if(this.phase == Phase.MENU){
            this.title = "MAIN MENU";
            this.options = new String[]{"start", "exit"};
            this.description = new String[]{"start the game", "quit the game"};
        }
        else if(this.phase == Phase.START){
            this.title = "START OPTIONS";
            this.options = new String[]{"new", "load"};
            this.description = new String[]{"start new game", "load previous game"};
        }
        else if(this.phase == Phase.NEW_GAME){
            this.title = "BOARD SETTINGS";
            this.options = new String[]{"number"};
            this.description = new String[]{"set the size of the board ('number' between 2 and 20)"};
        }
        else if(this.phase == Phase.GAME){
            this.title = "GAME CONTROL";
            this.options = new String[]{"number space number", "solve", "save", "stop", "exit"};
            this.description = new String[]{
                    "make change on the board",
                    "solve the game",
                    "save the game",
                    "go to the main menu",
                    "quit the game"
            };
        }
    }

    protected abstract void showOptions();
    protected abstract void gameMain() throws IOException;
    protected abstract void createNewGame() throws IOException;
    protected abstract void loadPreviousGame() throws IOException;
    protected abstract void playGame() throws IOException;
    protected abstract void quitGame();
}
