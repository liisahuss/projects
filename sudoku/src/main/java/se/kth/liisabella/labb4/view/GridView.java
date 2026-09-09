package se.kth.liisabella.labb4.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import se.kth.liisabella.labb4.model.SudokuUtilities;

import static se.kth.liisabella.labb4.model.SudokuUtilities.GRID_SIZE;
import static se.kth.liisabella.labb4.model.SudokuUtilities.SECTION_SIZE;
import static se.kth.liisabella.labb4.model.SudokuUtilities.SECTIONS_PER_ROW;

public class GridView {
    private Label[][] numberTiles;
    private GridPane numberPane;

    public GridView(){
        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        //...
        numberPane = makeNumberPane();
        //...
    }

    public GridPane getNumberPane(){
        return numberPane;
    }

    public Label getNumberTile(int row, int column){
        return numberTiles[row][column];
    }

    private void initNumberTiles(){
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for(int row=0; row<GRID_SIZE; row++){
            for(int col=0; col< GRID_SIZE; col++){
                Label title = new Label("");
                title.setPrefWidth(32);
                title.setPrefHeight(32);
                title.setFont(font);
                title.setAlignment(Pos.CENTER);
                title.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                numberTiles[row][col] = title;
            }
        }
    }

    public void addEventHandlers(SudokuGameController controller){
        for(int row=0; row<GRID_SIZE; row++){
            for(int col=0; col< GRID_SIZE; col++){
                int finalRow = row;
                int finalCol = col;
                numberTiles[row][col].setOnMouseClicked(mouseEvent -> controller.setSelectedRowAndColumn(finalRow, finalCol));

            }
        }
    }

    private final GridPane makeNumberPane(){
        GridPane root = new GridPane();
        root.setStyle("-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        for(int srow=0; srow < SECTIONS_PER_ROW; srow++){
            for(int scol=0; scol < SECTIONS_PER_ROW; scol++){
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                for(int row=0; row<SECTION_SIZE; row++){
                    for(int col=0; col<SECTION_SIZE; col++){
                        section.add(numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col], col, row);
                    }
                }
                root.add(section, scol, srow);
            }
        }
        return root;
    }

    public void setupCell(int row, int column, int value, boolean editable){
        Label title = numberTiles[row][column];
        if(value > 0){
            title.setText(String.valueOf(value));
        } else {
            title.setText("");
        }
        if(!editable){
            title.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            title.setTextFill(Color.WHITE);
        } else {
            title.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        }
    }

    public Label getTile(int row, int col){
        return numberTiles[row][col];
    }
}
