module se.kth.liisabella.labb4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.liisabella.labb4 to javafx.fxml;
    exports se.kth.liisabella.labb4.model;
    exports se.kth.liisabella.labb4.view;
    opens se.kth.liisabella.labb4.view to javafx.fxml;
}