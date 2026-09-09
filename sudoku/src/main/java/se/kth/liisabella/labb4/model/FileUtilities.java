package se.kth.liisabella.labb4.model;

import javafx.stage.FileChooser;

import java.io.*;
import java.util.SortedMap;

public class FileUtilities {
    public static boolean saveGameToFile(SudokuModel model) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("sudoku_game.sudoku");
        fileChooser.setTitle("Save Game");
        File file = fileChooser.showSaveDialog(null);
        if (file != null){
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(model);
                return true;
            } catch (IOException e){
                return false;
            }
        } return false;
    }

    public static SudokuModel loadGameFromFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Game");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
                return (SudokuModel) ois.readObject();
            } catch (IOException | ClassNotFoundException e){
                return null;
            }
        } return null;
    }
}
