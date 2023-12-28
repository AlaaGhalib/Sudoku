package se.kth.alialaa.labb4.model;

import javafx.stage.FileChooser;

/**
 * The SudokuIO class provides methods for saving and loading Sudoku game data.
 * It uses object serialization to save and load the state of the Sudoku game.
 */

import java.io.*;

public class SudokuIO {

    private SudokuIO() {

    }

    /**
     * Saves the Sudoku game state to a specified file using object serialization.
     *
     * @param file  The File object representing the file where the game state will be saved.
     * @param model The Cells object representing the Sudoku game state to be saved.
     * @throws RuntimeException If an IOException occurs during the saving process.
     */
    public static void SaveGame(File file, Cells model) throws IOException {
        if (file != null) {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(model);
            out.close();
        }
    }


    /**
     * Loads the Sudoku game state from a specified file using object deserialization.
     *
     * @param file The File object representing the file from which the game state will be loaded.
     * @return The Cells object representing the loaded Sudoku game state, or null if the file is null.
     * @throws RuntimeException If an IOException or ClassNotFoundException occurs during the loading process.
     */
    public static Cells LoadGame(File file) throws IOException, ClassNotFoundException {
        if (file != null) {
            Cells model;
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                model = (Cells) in.readObject();
                in.close();
                return model;
        }
        return null;   // Return null if the file is null
    }
}
