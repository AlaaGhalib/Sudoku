package se.kth.alialaa.labb4.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents the game board of a Sudoku puzzle.
 * Implements Serializable to support serialization.
 */
import static se.kth.alialaa.labb4.model.SudokuUtilities.GRID_SIZE;

public class Cells implements Serializable {
    private Cell[][] cells;                                          // The 2D array representing the Sudoku grid
    private int leftToGuess;                                         // The count of cells left to guess
    private SudokuUtilities.SudokuLevel level;                       // The difficulty level of the Sudoku puzzle

    /**
     * Constructs a new Cells object with the specified Sudoku level.
     *
     * @param level The difficulty level of the Sudoku puzzle.
     */
    public Cells(SudokuUtilities.SudokuLevel level) {
        this.cells = new Cell[GRID_SIZE][GRID_SIZE];
        this.leftToGuess = 81;
        this.level = level;
        fillCells(this.level);
    }

    /**
     * Gets the count of cells left to guess.
     *
     * @return The count of cells left to guess.
     */
    public int getLeftToGuess() {
        return leftToGuess;
    }

    /**
     * Gets a copy of the 2D array representing the Sudoku grid.
     *
     * @return A copy of the Sudoku grid.
     */
    public Cell[][] getCells() {
        Cell[][] copy = new Cell[GRID_SIZE][GRID_SIZE];
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                copy[row][col] = new Cell(cells[row][col].getToGuess(),cells[row][col].getCellState(),cells[row][col].getTheGuess());
            }
        }
        return copy;
    }

    /**
     * Handles a user's guess for a specific cell.
     *
     * @param rowNr The row number of the cell.
     * @param colNr The column number of the cell.
     * @param guess The user's guess for the cell.
     * @throws IllegalArgumentException If the guess is illegal (not between 1 and 9).
     */
    public void guess(int rowNr, int colNr, int guess) throws IllegalArgumentException{
        if(guess<=0 || guess>9) {
            throw new IllegalArgumentException("Illegal input!");
        }
        if (cells[rowNr][colNr].getCellState().equals(CellState.KNOWN)){
            return;
        }
        if(cells[rowNr][colNr].getTheGuess() != 0) {
            cells[rowNr][colNr].setTheGuess(guess);
        } else {
            cells[rowNr][colNr].setTheGuess(guess);
            leftToGuess--;
        }
    }

    /**
     * Checks if the user's guesses are correct so far.
     *
     * @return True if all guesses are correct, false otherwise.
     */
    public boolean Check() {
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                if (!cells[row][col].getCellState().equals(CellState.KNOWN) && cells[row][col].getTheGuess() != 0) {
                    if (!cells[row][col].isTheGuessCorrect()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Clears the user's guess for a specific cell if it is not a known cell.
     *
     * @param rowNr The row number of the cell.
     * @param colNr The column number of the cell.
     */
    public void clear(int rowNr, int colNr) {
        if(cells[rowNr][colNr].getCellState().equals(CellState.KNOWN)) {
            return;
        }else if(cells[rowNr][colNr].getTheGuess() != 0) {
            cells[rowNr][colNr].setTheGuess(0);
            leftToGuess++;
        }
    }

    /**
     * Clears the user's guess for all cells that are not known.
     */
    public void clearAll() {
        for (int row=0; row<GRID_SIZE; row++) {
            for(int col=0; col<GRID_SIZE; col++) {
                if(!cells[row][col].getCellState().equals(CellState.KNOWN)) {
                    cells[row][col].setTheGuess(0);
                    this.leftToGuess++;
                }
            }
        }
    }

    /**
     * Checks if there are no cells left to guess.
     *
     * @return True if there are no cells left to guess, false otherwise.
     */
    public boolean isTheLastGuess() {
        return leftToGuess == 0;
    }

    /**
     * Checks if the user has correctly guessed all cells and prints the result.
     *
     * @return True if all guesses are correct, false otherwise.
     */
    public boolean result() {
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                if(!cells[row][col].isTheGuessCorrect()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Provides a hint to the user by filling in a random empty cell with the correct number.
     */
    public void hint() {
        if (!isTheLastGuess()) {
            Random random = new Random();
            int rowRand;
            int colRand;
            do {
                rowRand = random.nextInt(9);
                colRand = random.nextInt(9);
            } while (cells[rowRand][colRand].getTheGuess() != 0);
            cells[rowRand][colRand].setTheGuess(cells[rowRand][colRand].getToGuess());
            this.leftToGuess--;
        }
    }

    /**
     * Resets the game by filling in a new Sudoku puzzle with the current difficulty level.
     */
    public void NewGame() {
        this.leftToGuess = 81;
        fillCells(this.level);
    }

    /**
     * Starts a new game with a specified difficulty level.
     *
     * @param level The difficulty level for the new game.
     */
    public void NewGameNewLevel(SudokuUtilities.SudokuLevel level) {
        this.leftToGuess = 81;
        fillCells(level);
    }

    /**
     * Provides information about how to play the game.
     *
     * @return A string containing instructions for playing the game.
     */
    public String gameInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1. Choose a square!").append("\n").append("2. Choose a number!").append("\n").
                append("To clear a square: Choose a square and press C!").append("\n").append("Press Hint for a hint!").append("\n")
                .append("Press Check to check if you are right so far!").append("\n").append("You win when every square has the right number!");
        return stringBuilder.toString();
    }

    /**
     * Fills the Sudoku grid with numbers based on the difficulty level.
     *
     * @param Level The difficulty level of the Sudoku puzzle.
     */
    private void fillCells(SudokuUtilities.SudokuLevel Level) {
        int[][][] sudokuMatrix = SudokuUtilities.generateSudokuMatrix(Level);
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                if (sudokuMatrix[row][col][0] != 0) {
                    cells[row][col] = new Cell(sudokuMatrix [row][col][1], CellState.KNOWN, sudokuMatrix[row][col][0]);
                    this.leftToGuess--;
                }
                else {
                    cells[row][col] = new Cell(sudokuMatrix[row][col][1], CellState.TO_GUESS, sudokuMatrix[row][col][0]);
                }
            }
        }
        blendCells();
    }

    private void blendCells() {
        Random random = new Random();
        int firstNumber, secondNumber;
        do {
            firstNumber = random.nextInt(9)+1;
            secondNumber = random.nextInt(9)+1;
        }while (firstNumber == secondNumber);

        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                if(cells[row][col].getToGuess() == firstNumber) {
                    for (int newRow=row; newRow<GRID_SIZE; newRow++) {
                        for (int newCol=0; newCol<GRID_SIZE; newCol++) {
                            if(cells[newRow][newCol].getToGuess() == secondNumber) {
                                Cell tmp = cells[row][col];
                                cells[row][col].setToGuess(cells[newRow][newCol].getToGuess());
                                cells[newRow][newCol].setToGuess(tmp.getToGuess());
                                break;
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }


    /**
     * Returns a string representation of the Sudoku grid.
     *
     * @return A string containing the user's guesses for each cell in the Sudoku grid.
     */
    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("[");
        for (int row=0; row<GRID_SIZE; row++) {
            info.append("{");
            for (int col=0; col<GRID_SIZE; col++) {
                info.append(cells[row][col].getTheGuess()).append(", ");
            }
            info.append("}").append("\n");
        }
        info.append("]");
        return info.toString();
    }

}