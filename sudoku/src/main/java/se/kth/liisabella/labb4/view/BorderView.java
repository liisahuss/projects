package se.kth.liisabella.labb4.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import se.kth.liisabella.labb4.model.SudokuUtilities;

import java.util.ArrayList;
import java.util.List;

public class BorderView {
    private VBox root; //root=det vi ger till App
    private MenuBar menuBar;
    private BorderPane layout;
    private GridView grid;
    private List<Button> numberButtons = new ArrayList<>();
    private Button clearCurrentNumberTileButton = new Button("C");
    private Button hintBtn = new Button("Hint");
    private Button checkBtn = new Button("Check");
    private Button clearBtn = new Button("Clear");
    private MenuItem saveItem = new MenuItem("Save Game");
    private MenuItem loadItem = new MenuItem("Load Game");
    private MenuItem newGameItem = new MenuItem("New Game");
    private MenuItem easyLevelItem = new MenuItem("Easy");
    private MenuItem mediumLevelItem = new MenuItem("Medium");
    private MenuItem hardLevelItem = new MenuItem("Hard");

    private MenuItem gameInfoItem = new MenuItem("Game Rules");
    private MenuItem clearAllItem = new MenuItem("Clear all tiles");
    private MenuItem checkBoardItem = new MenuItem("Check board");

    public BorderView(){
        //1. skapa grid
        grid = new GridView();

        //2. skapa menyrad
        menuBar = createMenuBar();

        //3. skapa vänster knapp-panel
        VBox leftPanel = createLeftPanel();

        //4. skapa höger knapp-panel
        VBox rightPanel = createRightPanel();

        //5. bygg boarder layout
        layout = new BorderPane();
        layout.setCenter(grid.getNumberPane());
        layout.setLeft(leftPanel);
        layout.setRight(rightPanel);

        //6. Bygg root-stapel
        root = new VBox();
        root.getChildren().addAll(menuBar, layout);
    }

    private MenuBar createMenuBar(){
        MenuBar bar = new MenuBar();

        Menu game = new Menu("Game");
        game.getItems().addAll(
                newGameItem,
                easyLevelItem,
                mediumLevelItem,
                hardLevelItem
        );
        Menu file = new Menu("File");
        file.getItems().addAll(saveItem, loadItem);

        Menu help = new Menu("Help");
        help.getItems().addAll(
                gameInfoItem,
                checkBoardItem,
                clearAllItem
        );

        bar.getMenus().addAll(game, file, help);
        return bar;
    }

    public void setupMenuButtonHandlers(SudokuGameController controller){
        loadItem.setOnAction(event -> {
            controller.loadGame();
        });
        saveItem.setOnAction(event -> {
            controller.saveGame();
        });
        checkBoardItem.setOnAction(event ->{
            controller.checkBoard();
        });
        clearAllItem.setOnAction(event ->{
            controller.handleClearAllUserNumbers();
        });
        newGameItem.setOnAction(event ->{
            controller.setupGame();
        });
        easyLevelItem.setOnAction(event -> {
            controller.setDifficultyLevel(SudokuUtilities.SudokuLevel.EASY);
            controller.setupGame();
        });
        mediumLevelItem.setOnAction(event -> {
            controller.setDifficultyLevel(SudokuUtilities.SudokuLevel.MEDIUM);
            controller.setupGame();
        });
        hardLevelItem.setOnAction(event -> {
            controller.setDifficultyLevel(SudokuUtilities.SudokuLevel.HARD);
            controller.setupGame();
        });
        gameInfoItem.setOnAction(event -> {
            controller.showGameRules();
        });
    }

    private VBox createRightPanel(){
        VBox rightBox = new VBox(5); //5 pixlars mellanrum
        rightBox.setPadding(new Insets(10));
        rightBox.setAlignment(Pos.TOP_CENTER);

        for(int i=1; i<= 9; i++){
            Button btn = new Button(String.valueOf(i));
            btn.setPrefWidth(50);
            rightBox.getChildren().add(btn);
            numberButtons.add(btn);
        }
        clearCurrentNumberTileButton.setPrefWidth(50);
        rightBox.getChildren().add(clearCurrentNumberTileButton);

        return rightBox;
    }

    private VBox createLeftPanel(){
        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(10));
        leftBox.setAlignment(Pos.TOP_CENTER);

        hintBtn.setPrefWidth(70);
        checkBtn.setPrefWidth(70);
        clearBtn.setPrefWidth(70);

        leftBox.getChildren().addAll(hintBtn, checkBtn, clearBtn);
        return leftBox;
    }

    public void setupClearCurrentNumberTileButtonHandler(SudokuGameController controller){
        clearCurrentNumberTileButton.setOnAction(e ->{
            controller.handleClearCurrentNumberTile();
        });
    }

    public void setupLeftSideButtonHandlers(SudokuGameController controller){
        clearBtn.setOnAction(e ->{
            controller.handleClearAllUserNumbers();
        });
        hintBtn.setOnAction(e -> {
            controller.giveHint();
        });
        checkBtn.setOnAction(e -> {
            controller.checkBoard();
        });
    }

    public void setupNumberButtonsHandler(SudokuGameController controller){
        this.getNumberButtons().forEach(button -> {
            button.setOnAction(e ->{
                System.out.println("number: " + button.getText());
                int number = Integer.parseInt(button.getText());
                controller.handleNumberClicked(number);
            });
        });
    }

    public VBox getRoot(){
        return root;
    }

    public GridView getGrid(){
        return grid;
    }

    public List<Button> getNumberButtons(){
        return numberButtons;
    }
}
