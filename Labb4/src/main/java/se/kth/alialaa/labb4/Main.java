package se.kth.alialaa.labb4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.kth.alialaa.labb4.controller.Controller;
import se.kth.alialaa.labb4.model.Cells;
import se.kth.alialaa.labb4.model.SudokuUtilities;
import se.kth.alialaa.labb4.view.Buttons;
import se.kth.alialaa.labb4.view.GridView;
import se.kth.alialaa.labb4.view.MenuView;

import java.util.concurrent.ThreadPoolExecutor;

public class Main extends Application{
    private GridView gridView;
    private Buttons buttons;
    private MenuView menuView;
    private BorderPane borderpane;
    private Controller controller;
    private Cells model;
    public static void main(String[] arg) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.model = new Cells(SudokuUtilities.SudokuLevel.EASY);
        initializeTheView();
        controller = new Controller(model,gridView,buttons,menuView,stage);

        Scene scene = new Scene(borderpane,662,572);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeTheView() {
        this.gridView = new GridView(model);
        this.buttons = new Buttons();
        this.menuView = new MenuView();
        this.borderpane = new BorderPane();

        borderpane.setLeft(buttons.getLeftVBox());
        borderpane.setRight(buttons.getRightVBox());
        borderpane.setCenter(gridView.getNumberPane());
        borderpane.setTop(menuView.getMenuBar());

    }

}
