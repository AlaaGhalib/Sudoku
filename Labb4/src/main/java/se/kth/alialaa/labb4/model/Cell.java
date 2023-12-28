package se.kth.alialaa.labb4.model;


import java.io.Serializable;

/**
 * Represents a cell in a Sudoku game.
 * Each cell contains a number to guess, a current state, and a user's guess.
 * Implements Serializable to support serialization.
 */
public class Cell implements Serializable {
    private int toGuess;
    private CellState cellState;
    private int theGuess;

    /**
     * Constructs a new Cell with the given parameters.
     *
     * @param toGuess   The number to guess in this cell.
     * @param cellState The initial state of the cell (TO_GUESS, KNOWN, RIGHT).
     * @param theGuess  The initial user's guess for this cell.
     */
    public Cell(int toGuess, CellState cellState, int theGuess) {
        this.toGuess = toGuess;
        this.cellState = cellState;
        this.theGuess = theGuess;
    }

    /**
     * Sets the state of the cell.
     *
     * @param cellState The new state of the cell (TO_GUESS, KNOWN, RIGHT).
     */
    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    /**
     * Sets the user's guess for this cell.
     *
     * @param theGuess The user's guess for this cell.
     */
    public void setTheGuess (int theGuess) {
        this.theGuess = theGuess;
    }

    public void setToGuess(int toGuess) {
        this.toGuess = toGuess;
    }

    /**
     * Gets the number to guess in this cell.
     *
     * @return The number to guess.
     */
    public int getToGuess() {
        return toGuess;
    }

    /**
     * Gets the user's guess for this cell.
     *
     * @return The user's guess.
     */
    public int getTheGuess() {
        return theGuess;
    }

    /**
     * Gets the current state of the cell.
     *
     * @return The current state of the cell (TO_GUESS, KNOWN, RIGHT).
     */
    public CellState getCellState() {
        return cellState;
    }

    /**
     * Checks if the user's guess is correct and updates the cell state accordingly.
     *
     * @return True if the guess is correct, false otherwise.
     */
    public boolean isTheGuessCorrect() {
        if(toGuess == theGuess) {
            this.cellState = (!this.getCellState().equals(CellState.KNOWN))? CellState.RIGHT: CellState.KNOWN;
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the cell.
     *
     * @return A string containing information about the cell.
     */
    @Override
    public String toString() {
        return "number to guess: " + toGuess;
    }
}
