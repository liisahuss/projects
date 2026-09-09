package se.kth.liisabella.labb4.view;


import se.kth.liisabella.labb4.model.SudokuModel;
import se.kth.liisabella.labb4.model.SudokuUtilities;
import static se.kth.liisabella.labb4.model.SudokuUtilities.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import se.kth.liisabella.labb4.model.FileUtilities;

public class SudokuGameController {
    private SudokuModel model;
    private BorderView view;

    public SudokuGameController(SudokuModel model, BorderView view){
        this.model = model;
        this.view = view;

        view.setupNumberButtonsHandler(this);
        view.setupClearCurrentNumberTileButtonHandler(this);
        view.getGrid().addEventHandlers(this);
        view.setupLeftSideButtonHandlers(this);
        view.setupMenuButtonHandlers(this);
    }

    public void setupGame(){
        model.setupGame(model.getCurrentLevel());
        updateView();
    }

    public void setDifficultyLevel(SudokuUtilities.SudokuLevel level){
        model.setCurrentLevel(level);
    }

    public void setSelectedRowAndColumn(int row, int col){
        System.out.println("row: " + row + ", col: " + col + " ");
        if(row==-1 || col == -1){
            return;
        }
        model.setSelectedRow(row);
        model.setSelectedCol(col);
    }

    public void handleNumberClicked(int number){
        int row = model.getSelectedRow();
        int col = model.getSelectedCol();

        if(number==-1 || row==-1 || col==-1){
            return;
        }
        model.setValue(row, col, number);
        updateView();
        checkWinCondition();
    }

    public void handleClearCurrentNumberTile(){
        int row = model.getSelectedRow();
        int col = model.getSelectedCol();

        if(row==-1 || col==-1){
            return;
        }
        model.clearCell(row, col);
        updateView();
    }

    public void handleClearAllUserNumbers(){
        model.reset();
        updateView();
    }

    public void updateView(){
        for(int r=0; r < GRID_SIZE; r++){
            for(int c=0; c < GRID_SIZE; c++){
                int val = model.getCellNumber(r, c);
                Label label = view.getGrid().getTile(r, c);
                if(val<1){
                    label.setText("");
                } else {
                    label.setText(String.valueOf(val));
                }
                if(model.isCellFixed(r, c)){
                    label.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; -fx-border-width: 0.5px");
                } else {
                    label.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 0.5px");
                }
            }
        }
    }

    public void checkBoard(){
        boolean isSolved = model.isSolved();
        if(isSolved){
            showAlert("Check board", "Board is correct");
        }
        else {
            showAlert("Check board", "Board has incorrect values");
        }
    }

    public void giveHint(){
       model.getHint();
       updateView();
    }

    public void checkWinCondition(){
       if(!model.isBoardComplete()){
           return;
       }
       boolean isCompleteAndCorrect = model.isBoardCompleteAndCorrect();
       if(isCompleteAndCorrect){
           showAlert("Sudoku Check", "You solved the board!");
       } else {
           showAlert("Sudoku check", "The board isn't correct");
       }
    }

    public void saveGame(){
        if(!FileUtilities.saveGameToFile(model)){
            showAlert("Save game failed", "Save game failed");
        }
    }

    public void loadGame(){
        SudokuModel loadedModel = FileUtilities.loadGameFromFile();
        if(loadedModel != null){
            this.model = loadedModel;
            updateView();
        } else {
            showAlert("Sudoku Game", "Could not load game file.");
        }
    }

    public void showGameRules(){
        String gameRules = """
                Sudoku grid consists of 9x9 spaces.
                You can only use numbers from 1 to 9.
                Each 3x3 block can only contain numbers from 1 to 9.
                Each vertical column can only contain numbers fom 1 to 9.
                Each horizontal row can only contain numbers from 1 to 9.
                Each number in the 3x3 block, vertical column or horizontal row can only be used once.
                The game is over when the whole Sudoku grid is correctly filled with numbers.
                """;
        showAlert("Sudoku Rules", gameRules);
    }

    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}