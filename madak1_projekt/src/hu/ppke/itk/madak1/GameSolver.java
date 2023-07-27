package hu.ppke.itk.madak1;

import java.util.ArrayList;

public class GameSolver {

    /**
     * Pair class
     *  - It stores an int pair
     */
    private static class IntPair{
        private final int first;
        private final int second;

        IntPair(int first, int second){
            this.first = first;
            this.second = second;
        }
        IntPair(String intPair){
            this.first = Integer.parseInt(intPair.substring(0, intPair.indexOf(' ')));
            this.second = Integer.parseInt(intPair.substring(intPair.indexOf(' ')+1));
        }

        @Override
        public String toString() {
            return this.first + " " + this.second;
        }
    }

    private final ArrayList<IntPair> oddSolverSteps;
    private final ArrayList<IntPair> evenSolverSteps1;
    private final ArrayList<IntPair> evenSolverSteps2;

    /**
     * Constructor
     *  - Init some variables
     */
    public GameSolver(){
        this.oddSolverSteps = new ArrayList<>();
        this.evenSolverSteps1 = new ArrayList<>();
        this.evenSolverSteps2 = new ArrayList<>();
    }

    /**
     * Getter for odd solver
     *  - Return with the steps what the program and the user do
     * @return Odd solver
     */
    public ArrayList<IntPair> getOddSolverSteps() {
        return this.oddSolverSteps;
    }

    /**
     * Add step to a solver
     *  - If in the array then remove
     *  - Else add to array
     * @param solver The solver
     * @param xStep X coordinate
     * @param yStep Y coordinate
     */
    public void addStepToSolver(ArrayList<IntPair> solver, int xStep, int yStep){
        IntPair newPair = new IntPair(xStep + 1, yStep + 1);
        int target = this.findInSolver(solver, newPair);

        if(target != -1) solver.remove(target);
        else solver.add(newPair);
    }

    /**
     * Add step to a solver (string)
     *  - If in the array then remove
     *  - Else add to array
     * @param solver The solver
     * @param step The step
     */
    public void addStepToSolver(ArrayList<IntPair> solver, String step){
        IntPair newPair = new IntPair(step);
        int target = this.findInSolver(solver, newPair);

        if(target != -1) solver.remove(target);
        else solver.add(newPair);
    }

    /**
     * Find step in solver array
     *  - Return with the index of the step
     * @param solver The solver
     * @param pair The step pair we want to find
     * @return The step index or -1
     */
    private int findInSolver(ArrayList<IntPair> solver, IntPair pair){
        for (int idx = 0; idx < solver.size(); idx++)
            if (solver.get(idx).toString().equals(pair.toString())) return idx;
        return -1;
    }

    /**
     * Print odd solver
     *  - Return the string format of the odd solution
     * @return Odd solver solution
     */
    public String printOddSolverSteps(){
        return this.oddSolverSteps.toString();
    }

    /**
     * Print even solver
     *  - Find 2 solution and return the better one
     * @param board The board
     * @return The better solution
     */
    public String printEvenSolverSteps(int[][] board){
        this.evenSolverSteps1.clear();
        this.evenSolverSteps2.clear();
        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board.length; y++){
                if (board[x][y] == 0) this.crossInput(board, x, y, 1);
                else this.crossInput(board, x, y, 2);
            }
        }
        if(this.evenSolverSteps1.size() < this.evenSolverSteps2.size()) return this.evenSolverSteps1.toString();
        else return this.evenSolverSteps2.toString();
    }

    /**
     * Cross input
     *  - Add the coordinates in the solver which in the cross
     * @param board The board
     * @param rowIndex The X coordinate
     * @param columnIndex The Y coordinate
     * @param solverID Which solver array
     */
    private void crossInput(int[][] board, int rowIndex, int columnIndex, int solverID){
        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board.length; y++){
                if(rowIndex == x || columnIndex == y){
                    if(solverID==1) this.addStepToSolver(this.evenSolverSteps1, x, y);
                    else this.addStepToSolver(this.evenSolverSteps2, x, y);
                }
            }
        }
    }

}
