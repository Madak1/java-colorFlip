package hu.ppke.itk.madak1;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleMaster extends GameMaster {

    private final BufferedReader input;
    private long startTime;

    /**
     * ConsoleMaster constructor
     *  - Init the reader
     *  - Set the phase to Menu phase
     */
    public ConsoleMaster(){
        this.input = new BufferedReader(new InputStreamReader(System.in));
        super.phaseChanger(Phase.MENU);
    }

    /**
     * Show the options on the console
     *  - Print the title of the phase
     *  - Print all option we can do at the actual phase and a description for them
     */
    @Override
    protected void showOptions() {
        System.out.println("\n" + super.title);
        for (int i = 0; i < super.options.length; i++)
            System.out.println(" - '" + super.options[i] + "'\tto " + super.description[i]);
    }

    /**
     * The main func for the console mode
     *  - It handles the console mode of the game
     * @throws IOException The exception what the program could throw
     */
    @Override
    protected void gameMain() throws IOException {
        while (super.phase != Phase.EXIT){
            String command = this.commandLoop();
            if(command.equals("start")) super.phaseChanger(Phase.START);
            else super.phaseChanger(Phase.EXIT);

            if(super.phase == Phase.START){
                command = this.commandLoop();
                if(command.equals("new")) super.phaseChanger(Phase.NEW_GAME);
                else super.phaseChanger(Phase.LOAD_GAME);

                if(super.phase == Phase.NEW_GAME) this.createNewGame();
                else this.loadPreviousGame();
                super.phaseChanger(Phase.GAME);

                this.playGame();
            }
        }
        this.quitGame();
    }

    /**
     * Command loop
     *  - Show the options
     *  - Ask input while is it correct
     * @return The correct command
     * @throws IOException The exception what the program could throw
     */
    private String commandLoop() throws IOException{
        this.showOptions();
        String command;
        do command = this.newCommand();
        while(!Arrays.asList(super.options).contains(command));
        return command;
    }

    /**
     * New command
     *  - Print a text and ask input
     * @return The text what the user write in the console
     * @throws IOException The exception what the program could throw
     */
    private String newCommand() throws IOException {
        System.out.print("Your command: ");
        return input.readLine();
    }

    /**
     * Setup new game
     *  - Show the options
     *  - Ask input while it is a number between 2 and 20
     *  - Generate the board
     * @throws IOException The exception what the program could throw
     */
    @Override
    protected void createNewGame() throws IOException {
        this.showOptions();
        String command;
        do command = this.newCommand();
        while(!Pattern.compile("[2-9]|(1\\d)|20").matcher(command).matches());
        System.out.println("\nSTART NEW GAME");
        super.logic = new GameLogic(Integer.parseInt(command));
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Setup previous game
     *  - Try load previous game
     *     --> If the program can't load previous game then start a new one
     * @throws IOException The exception what the program could throw
     */
    @Override
    protected void loadPreviousGame() throws IOException {
        try {
            FileInputStream fileInput = new FileInputStream("savedGame.txt");
            System.out.println("\nLOAD PREVIOUS GAME");
            super.logic = new GameLogic(fileInput);
            fileInput.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nLOAD PREVIOUS GAME FAIL (NO SAVED GAME FOUND --> START NEW GAME)");
            super.phaseChanger(Phase.NEW_GAME);
            this.createNewGame();
        } catch (IOException e) {
            e.getStackTrace();
        }
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("savedGame.txt")));
        this.startTime = System.currentTimeMillis() - Integer.parseInt(input.readLine()) * 1000L;
    }

    /**
     * The game handler
     *  - Show the options
     *  - Ask input
     *  - Check the user win the game or not
     * @throws IOException The exception what the program could throw
     */
    @Override
    protected void playGame() throws IOException {
        this.showOptions();
        while (super.phase == Phase.GAME){
            System.out.println(super.logic);
            this.gameCommandHandler(this.newCommand());
            if(super.logic.winCheck()){
                System.out.println(super.logic);
                System.out.println("YOU WIN! (STEPS: " + super.logic.getStepCounter() + ")");
                super.phaseChanger(Phase.MENU);
            }
        }
    }

    /**
     * Command handler
     *  - Set the action to the options
     * @param command The command what the user write
     * @throws IOException The exception what the program could throw
     */
    private void gameCommandHandler(String command) throws IOException {
        switch (command){
            case "save" -> {
                super.logic.saveGame((System.currentTimeMillis() - this.startTime)/1000L);
                System.out.println("\nGAME IS SAVED");
            }
            case "solve" -> System.out.print("\nSOLUTION: " + super.logic.solver() + "\n");
            case "stop" -> {
                super.phaseChanger(Phase.MENU);
                System.out.println("\nGO TO THE MENU");
            }
            case "exit" -> super.phaseChanger(Phase.EXIT);
            default -> {
                Matcher coordinateMatcher = Pattern.compile("([1-9]\\d*) ([1-9]\\d*)").matcher(command);
                if(!coordinateMatcher.matches()) {
                    System.out.println("\nWRONG FORMAT (CORRECT: 'number space number' WHERE [number > 0])");
                } else {
                    int rowIndex = Integer.parseInt(coordinateMatcher.group(1));
                    int columnIndex = Integer.parseInt(coordinateMatcher.group(2));
                    if(rowIndex > super.logic.getBoardSize() || columnIndex > super.logic.getBoardSize()){
                        if(rowIndex > super.logic.getBoardSize())
                            System.out.println("WRONG NUMBER (ROW TOO BIG)");
                        if(columnIndex > super.logic.getBoardSize())
                            System.out.println("WRONG NUMBER (COLUMN TOO BIG)");
                    }else super.logic.newInput(rowIndex-1, columnIndex-1);
                }
            }
        }
    }

    /**
     * The quit handler
     *  - Print the end text and quit the game
     */
    @Override
    protected void quitGame() {
        System.out.println("\nQUIT THE GAME");
        System.exit(0);
    }
}
