package se.kth.alialaa.labb4.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import se.kth.alialaa.labb4.controller.Controller;

/**
 * Represents a set of buttons for a Sudoku game.
 */
public class Buttons {
    private Button check, hint, clear;
    private Button[] numberedButton;
    private VBox leftVBox, rightVBox;
    private int selectedButton;
    private Controller controller;

    /**
     * Constructs a new Buttons object, initializing the buttons and event handlers.
     */
    public Buttons() {
        initializeButtons();
        this.leftVBox = new VBox(2, this.check, this.hint);
        refineVbox(leftVBox);
        this.rightVBox = new VBox(2, numberedButton);
        this.rightVBox.getChildren().add(clear);
        refineVbox(rightVBox);
        this.selectedButton = -1;
        addEvenentHandler();
    }

    /**
     * Gets the VBox containing the left buttons (Check and Hint).
     *
     * @return The VBox containing the left buttons.
     */
    public VBox getLeftVBox() {
        return leftVBox;
    }

    /**
     * Gets the VBox containing the right buttons (Numbered buttons and Clear).
     *
     * @return The VBox containing the right buttons.
     */
    public VBox getRightVBox() {
        return rightVBox;
    }

    /**
     * Gets the currently selected numbered button (1-9).
     *
     * @return The currently selected numbered button, or -1 if none is selected.
     */
    public int getSelectedButton() {
        return selectedButton;
    }

    /**
     * Sets the controller for handling button actions.
     *
     * @param controller The controller to be set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setSelectedButton(int selectedButton) {
        this.selectedButton = selectedButton;
    }

    /**
     * Initializes the buttons (Check, Hint, Clear, Numbered buttons).
     */
    private void initializeButtons() {
        this.check = new Button("Check");
        this.hint = new Button("Hint");
        this.clear = new Button("C");
        this.numberedButton = createNumberedButtons();
    }

    /**
     * Creates an array of numbered buttons (1-9).
     *
     * @return An array of numbered buttons.
     */
    private Button[] createNumberedButtons() {
        Button[] buttons = new Button[9];
        for (int i=0; i<9; i++) {
            buttons[i] = new Button(String.valueOf(i+1));
        }
        return buttons;
    }

    /**
     * Sets the alignment and padding for a VBox.
     *
     * @param theVBox The VBox to be refined.
     */
    private void refineVbox(VBox theVBox) {
        theVBox.setAlignment(Pos.CENTER);
        theVBox.setPadding(new Insets(10));
    }

    public boolean isValid() {
        return selectedButton>0 && selectedButton<=9;
    }

    /**
     * Adds event handlers to the numbered buttons, clear button, check button, and hint button.
     */
    public void addEvenentHandler() {
        EventHandler numberedButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i=0; i<numberedButton.length; i++) {
                    if(actionEvent.getSource().equals(numberedButton[i])) {
                        selectedButton = i+1;
                        //controller.handleGuess();
                    }
                }
            }
        };
        for (int i=0; i<numberedButton.length; i++) {
            numberedButton[i].setOnAction(numberedButtonHandler);
        }

        EventHandler clearHandler = new EventHandler<ActionEvent>() {
             @Override
            public void handle(ActionEvent actionEvent) {
                 selectedButton = 0;
                 //controller.handleClear();
             }
        };
        clear.setOnAction(clearHandler);


        EventHandler checkHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleCheck();
            }
        };
        check.setOnAction(checkHandler);


        EventHandler hintHandler = new  EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                controller.handleHint();
                controller.result();
            }
        };
        hint.setOnAction(hintHandler);
    }
}
