package se.kth.alialaa.labb4.controller;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.kth.alialaa.labb4.model.Cells;
import se.kth.alialaa.labb4.model.SudokuIO;
import se.kth.alialaa.labb4.model.SudokuUtilities;
import se.kth.alialaa.labb4.view.Buttons;
import se.kth.alialaa.labb4.view.GridView;
import se.kth.alialaa.labb4.view.MenuView;

import java.io.*;

/**
 * Represents the controller for the Sudoku application, handling interactions between the model and views.
 */
public class Controller {
    private Cells model;
    private GridView gridView;
    private Buttons buttons;
    private MenuView menuView;
    private Stage stage;

    /**
     * Constructs a new Controller with the specified model, grid view, buttons, menu view, and stage.
     *
     * @param model     The model representing the Sudoku game.
     * @param gridView  The grid view displaying the Sudoku board.
     * @param buttons   The buttons view providing user interaction buttons.
     * @param menuView  The menu view providing menu options.
     * @param stage     The JavaFX stage for the application.
     */
    public Controller(Cells model, GridView gridView, Buttons buttons, MenuView menuView, Stage stage) {
        this.model = model;
        this.gridView = gridView;
        this.buttons = buttons;
        this.menuView = menuView;
        this.stage = stage;

        gridView.setController(this);
        buttons.setController(this);
        menuView.setController(this);
    }

    /**
     * Handles the event when a square on the grid is clicked, updating the grid view.
     */
    public void handleTheChosenSquare() {
        gridView.updateView();
    }

    /**
     * Handles the event when a guess is made, updating the model and grid view.
     */
    public void handleGuess() {
        if(buttons.isValid()) {
            model.guess(gridView.getClickedRow(), gridView.getClickedCol(), buttons.getSelectedButton());
            gridView.updateView();
            System.out.println(model.getLeftToGuess());
        }
    }

    /**
     * Handles the event when the user checks the current state of the Sudoku board.
     * Displays an alert with the result.
     */
    public void handleCheck() {
        Alert alert = menuView.alertWindow("Result so far!", "information!");
        if (model.Check()){
            alert.setContentText("Correct so far!!");
        }else {
            alert.setContentText("Sorry! You have done some mistakes!!");
        }
        alert.show();
        gridView.updateView();
    }

    /**
     * Handles the event when the user clears a guess in a square, updating the model and grid view.
     */
    public void handleClear() {
        if (!buttons.isValid()) {
            model.clear(gridView.getClickedRow(), gridView.getClickedCol());
            gridView.updateView();
            System.out.println(model.getLeftToGuess());
        }
    }

    /**
     * Handles the event when the user requests a hint, updating the model and grid view.
     */
    public void handleHint() {
        model.hint();
        gridView.updateView();
    }

    /**
     * Handles the event when the user starts a new game, resetting the model and updating the grid view.
     */
    public void handleNewGame() {
        model.NewGame();
        gridView.updateView();
    }

    /**
     * Handles the event when the user starts a new game with a specified level, resetting the model and updating the grid view.
     *
     * @param level The difficulty level of the new game.
     */
    public void handleNewGameNewLevel(SudokuUtilities.SudokuLevel level) {
        model.NewGameNewLevel(level);
        gridView.updateView();
    }

     /**
     * Handles the action of saving the current state of the Sudoku game to a file.
     * Opens a FileChooser dialog for the user to specify the file where the game state will be saved.
     * If a valid file is selected, the current Sudoku game state is saved to that file.
     */
    public void handleSaveGame() {
        File file = menuView.makeFileChooser("Save Game", stage);
        if(file!=null) {
            try {
                SudokuIO.SaveGame(file, model);
            } catch (IOException e) {
                Alert alert = menuView.alertWindow("Exception!", "Exception!");
                alert.setContentText("Exception line 126 while saving game!");
                alert.show();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Handles the action of loading a Sudoku game from a file.
     * Opens a FileChooser dialog for the user to select a file to load the game state from.
     * If a valid file is selected, the Sudoku game state is loaded and the view is updated.
     */
    public void handleLoadGame() {
        File file = menuView.makeFileChooser("Load Game", stage);
        Cells modelFromFiles = null;
        try {
            modelFromFiles = SudokuIO.LoadGame(file);
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = menuView.alertWindow("Exception!", "Exception!");
            alert.setContentText("Exception line 144 while Loading game!");
            alert.show();
            throw new RuntimeException(e);
        }
        if (modelFromFiles != null) {
            model = modelFromFiles;
            gridView.setModel(model);
            gridView.updateView();
        }
    }

    /**
     * Handles the event when the user requests information about how to play the game.
     * Displays an alert with the game information.
     */
    public void handleInfo() {
        Alert alert = menuView.alertWindow("How to play!", "Information!");
        alert.setContentText(model.gameInfo());
        alert.show();
    }

    /**
     * Handles the event when the user clears all guesses on the board.
     * Updates the model and grid view.
     */
    public void handleClearAll() {
        model.clearAll();
        gridView.updateView();
    }

    /**
     * Checks the game result and displays a congratulatory or informative alert if the game is won or lost.
     */
    public void result() {
        if(model.getLeftToGuess() == 0) {
            Alert alert = menuView.alertWindow("Result!", "Information!");
            if(model.result()) {
                alert.setContentText("congratulations!! You Won!!!!");
            }else {
                alert.setContentText("Nice try!!!");
            }
            alert.show();
        }
    }

    /**
     * Handles the event when the user requests to exit the application.
     * Closes the application stage.
     */
    public void handleExit() {
        stage.close();
    }
}
