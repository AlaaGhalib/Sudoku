package se.kth.alialaa.labb4.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import se.kth.alialaa.labb4.controller.Controller;
import se.kth.alialaa.labb4.model.CellState;
import se.kth.alialaa.labb4.model.Cells;

import static se.kth.alialaa.labb4.model.SudokuUtilities.*;

/**
 * Represents the view for a Sudoku grid in the UI.
 */
public class GridView {
    private Cells model;
    private Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private TilePane numberPane;
    private int clickedRow, clickedCol;
    private Controller controller;

    /**
     * Constructs a new GridView with the specified model.
     *
     * @param model The model representing the Sudoku cells.
     */
    public GridView(Cells model) {
        this.model = model;
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
        this.clickedRow = -1;
        this.clickedCol = -1;
        addEventHandler();
    }

    /**
     * Sets the model for the GridView.
     *
     * @param model The new model to be set.
     */
    public void setModel(Cells model) {
        this.model = model;
    }


    /**
     * Gets the TilePane containing the Sudoku grid.
     *
     * @return The TilePane containing the Sudoku grid.
     */
    public TilePane getNumberPane() {
        return numberPane;
    }

    /**
     * Gets the row index of the last clicked square.
     *
     * @return The row index of the last clicked square.
     */
    public int getClickedRow () {
        return clickedRow;
    }

    /**
     * Gets the column index of the last clicked square.
     *
     * @return The column index of the last clicked square.
     */
    public int getClickedCol() {
        return clickedCol;
    }

    /**
     * Sets the controller for handling UI events.
     *
     * @param controller The controller to be set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }


    // ... (rest of the existing methods)

    /**
     * Initializes the number tiles based on the model.
     */
    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        int displayValue;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                displayValue = model.getCells()[row][col].getTheGuess();
                String displayValueStr = (displayValue !=0 ) ? String.valueOf(displayValue) : " ";
                Label tile = new Label(displayValueStr); // data from model
                tile.setPrefWidth(CELL_SIZE);
                tile.setPrefHeight(CELL_SIZE);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-background-color:"
                        + (model.getCells()[row][col].getCellState().equals(CellState.KNOWN)? "#f2f2f2" : "white"+ ";")); // css style
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }

    /**
     * Creates the TilePane representing the Sudoku grid.
     *
     * @return The TilePane representing the Sudoku grid.
     */
    private final TilePane makeNumberPane() {
        // create the root tile pane
        TilePane root = new TilePane();
        root.setPrefColumns(SECTIONS_PER_ROW);
        root.setPrefRows(SECTIONS_PER_ROW);
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        TilePane[][] sections = new TilePane[SECTIONS_PER_ROW][SECTIONS_PER_ROW];
        int i = 0;
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                TilePane section = new TilePane();
                section.setPrefColumns(SECTION_SIZE);
                section.setPrefRows(SECTION_SIZE);
                section.setStyle( "-fx-border-color: black; -fx-border-width: 0.5px;");

                // add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.getChildren().add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col]);
                    }
                }

                // add the section to the root tile pane
                root.getChildren().add(section);
            }
        }

        return root;
    }

    /**
     * Updates the background color of the Sudoku grid based on the model and user interactions.
     */
    public void updateColor() {
        for (int row=0; row<GRID_SIZE; row++) {
            for (int col=0; col<GRID_SIZE; col++) {
                numberTiles[row][col].setStyle("-fx-border-color: black; -fx-border-width: 0.5px; -fx-background-color:" +
                        (model.getCells()[row][col].getCellState().equals(CellState.KNOWN) ? "#f2f2f2" :
                                (row == clickedRow && col == clickedCol && !model.getCells()[row][col].getCellState().equals(CellState.KNOWN) ? "lightblue" : "white") + ";"));
            }
        }
    }

    /**
     * Updates the view of the Sudoku grid.
     */
    public void updateView() {
        if (model != null) {
            String displayValue;
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    displayValue = (model.getCells()[row][col].getTheGuess() != 0) ? String.valueOf(model.getCells()[row][col].getTheGuess()) : "";
                    numberTiles[row][col].setText(displayValue);
                }
            }
            updateColor();
        }
    }

    /**
     * Validates whether the current clicked square coordinates are within the valid range (0-8).
     *
     * @return True if the clicked square coordinates are valid, false otherwise.
     */
    public boolean isValid() {
        return getClickedRow()<=8 && getClickedRow()>=0 && getClickedCol()<=8 && getClickedCol()>=0;
    }

    /**
     * Adds a mouse click event handler for the Sudoku grid squares.
     */
    public void addEventHandler() {
         EventHandler tileClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int row=0; row <GRID_SIZE; row++) {
                    for (int col=0; col<GRID_SIZE; col++) {
                        if(event.getSource() == numberTiles[row][col]) {
                            clickedRow = row;
                            clickedCol = col;
                            controller.handleTheChosenSquare();
                            controller.handleGuess();
                            controller.result();
                            controller.handleClear();
                        }
                    }
                }
            }
        };
         for (int row=0; row<GRID_SIZE; row++) {
             for(int col=0; col<GRID_SIZE; col++) {
                 numberTiles[row][col].setOnMouseClicked(tileClickHandler); // add your custom event handler
             }
         }
    }

}
