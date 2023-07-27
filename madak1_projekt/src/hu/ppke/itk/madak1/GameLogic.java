package hu.ppke.itk.madak1;

import java.io.*;
import java.util.Random;

public class GameLogic {

    private final int boardSize;
    private int[][] board;
    private int stepCounter;
    private final GameSolver solver;

    /**
     * Logic constructor (new game)
     *  - Init some variables
     * @param boardSize The number of the tiles
     */
    public GameLogic(int boardSize){
        this.boardSize = boardSize;
        this.stepCounter = 0;
        this.solver = new GameSolver();
        this.boardFilling();
    }

    /**
     * Logic constructor (load game)
     *  - Try load the saved variables
     * @param loadGameFile The file path
     * @throws IOException This exception will throw if the load is fail
     */
    public GameLogic(InputStream loadGameFile) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(loadGameFile));

        input.readLine();

        this.boardSize = Integer.parseInt(input.readLine());
        this.board = new int[this.boardSize][this.boardSize];
        this.stepCounter = Integer.parseInt(input.readLine());
        this.solver = new GameSolver();

        int stepsSize = Integer.parseInt(input.readLine());
        for(int i = 0; i < stepsSize; i++)
            this.solver.addStepToSolver(this.solver.getOddSolverSteps(), input.readLine());

        for(int x = 0; x < this.boardSize; x++){
            for(int y = 0; y < this.boardSize; y++){
                this.board[x][y] = Integer.parseInt(input.readLine());
            }
        }
    }

    /**
     * Save game
     *  - Save the game to a file
     * @param time The elapsed time
     * @throws IOException The exception what the program could throw
     */
    public void saveGame(long time) throws IOException{
        PrintWriter output = new PrintWriter(new FileWriter("savedGame.txt"));

        output.println(time);

        output.println(this.boardSize);
        output.println(this.stepCounter);
        output.println(this.solver.getOddSolverSteps().size());

        for(int i = 0; i < this.solver.getOddSolverSteps().size(); i++){
            output.println(this.solver.getOddSolverSteps().get(i));
        }
        for(int x = 0; x < this.boardSize; x++){
            for(int y = 0; y < this.boardSize; y++){
                output.println(this.board[x][y]);
            }
        }

        output.close();
    }

    /**
     * Getter for board size
     *  - Get the number of the tiles
     * @return Number of the tiles
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Getter for the board
     *  - Get the board
     * @return The board
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Getter for the stepsNum
     *  - Get the number of the steps
     * @return The number of the steps
     */
    public int getStepCounter(){
        return this.stepCounter;
    }

    /**
     * Board filler
     *  - Fill the board random if tiles num is even
     *  - Fill the board whit some program input if tiles num is odd
     *  - Retry if the random board is fill whit just ones or zeros
     */
    private void boardFilling(){
        Random rnd = new Random();
        do{
            this.board = new int[this.boardSize][this.boardSize];
            if(this.boardSize % 2 == 0){
                for(int x = 0; x < this.boardSize; x++){
                    for(int y = 0; y < this.boardSize; y++){
                        this.board[x][y] = rnd.nextInt(2);
                    }
                }
            }else{
                int nullOrOne = rnd.nextInt(2);
                for(int x = 0; x < this.boardSize; x++){
                    for(int y = 0; y < this.boardSize; y++){
                        this.board[x][y] = nullOrOne;
                    }
                }

                double min = this.boardSize/2f;
                double max = Math.pow(this.boardSize, 1.5);
                double randRep = rnd.nextDouble((max - min) + 1) + min;
                for(int i = 0; i < randRep; i++){
                    this.newInput(rnd.nextInt(this.boardSize), rnd.nextInt(this.boardSize));
                    this.stepCounter--;
                }
            }
        }while(this.winCheck());
    }

    /**
     * New input
     *  - Save the step coordinates
     *  - Increase the step number
     *  - Make change on board
     * @param rowIndex Row index
     * @param columnIndex Column index
     */
    public void newInput(int rowIndex, int columnIndex){

        this.solver.addStepToSolver(this.solver.getOddSolverSteps(), rowIndex, columnIndex);
        this.stepCounter++;

        for(int x = 0; x < this.boardSize; x++){
            for(int y = 0; y < this.boardSize; y++){
                if(rowIndex == x || columnIndex == y){
                    if(this.board[x][y] == 0) this.board[x][y] = 1;
                    else this.board[x][y] = 0;
                }
            }
        }
    }

    /**
     * Win checker
     *  - Check the board
     * @return Win or not
     */
    public boolean winCheck(){
        int first = this.board[0][0];
        for(int x = 0; x < this.boardSize; x++){
            for(int y = 0; y < this.boardSize; y++){
                if(this.board[x][y] != first) return false;
            }
        }
        return true;
    }

    /**
     * Solver caller
     *  - It calls even or odd solver
     * @return The solution
     */
    public String solver(){
        if(this.boardSize % 2 == 0) return this.solver.printEvenSolverSteps(this.board);
        else return this.solver.printOddSolverSteps();
    }

    /**
     * ToString
     *  - Make text from the board array
     * @return The string version of the board
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n");
        for(int x = 0; x < this.boardSize; x++){
            for(int y = 0; y < this.boardSize; y++){
                stringBuilder.append(this.board[x][y]).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
