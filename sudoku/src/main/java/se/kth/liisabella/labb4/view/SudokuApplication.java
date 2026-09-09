package se.kth.liisabella.labb4.view;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import se.kth.liisabella.labb4.model.SudokuModel;

import java.io.IOException;

public class SudokuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Starting app");
        BorderView borderView = new BorderView();

        SudokuModel model = new SudokuModel();
        SudokuGameController controller = new SudokuGameController(model, borderView);
        controller.setupGame();
        controller.updateView();

        Scene scene = new Scene(borderView.getRoot());

        stage.setTitle("This is SUDOKU!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}