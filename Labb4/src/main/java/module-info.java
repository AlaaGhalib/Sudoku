module se.kth.alialaa.labb4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.alialaa.labb4 to javafx.fxml;
    exports se.kth.alialaa.labb4;
    exports se.kth.alialaa.labb4.model;
    opens se.kth.alialaa.labb4.model to javafx.fxml;
}