package controller;


import Models.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    public Label lives;
    @FXML
    public Text actions;

    @FXML
    private GridPane myGridPane;
    private final int gridSize = 11;

    @FXML
    public void initialize() {
        myGridPane.setStyle("-fx-background-color: #8B4513;");
        myGridPane.setGridLinesVisible(true);
        initializeGame();
    }
    @FXML
    private void restartGame() {
        myGridPane.getChildren().clear();
        myGridPane.setGridLinesVisible(true);
        initializeGame();
    }

    private void initializeGame() {
        Board board = new Board();
        fillGridFromBoard(board.getBoard());
    }

    private void fillGridFromBoard(int[][] boardArray) {
        for (int row = 0; row < boardArray.length; row++) {
            for (int col = 0; col < boardArray[row].length; col++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("grid-cell");
                myGridPane.add(pane, col, row);

                int cellValue = boardArray[row][col];
                switch (cellValue) {
                    case 1:
                        addEnemy(col, row, 1);
                        break;
                    case 2:
                        addEnemy(col, row, 2);
                        break;
                    case 3:
                        addHero(col, row);
                        break;
                    case 4:
                        addStone(col, row);
                        break;
                    case 5:
                        addTree(col, row);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void addEnemy(int col, int row, int lives) {
        Polygon enemy = new Polygon();
        if (lives == 1) {
            enemy.getPoints().addAll(0.0, 0.0, 30.0, 0.0, 15.0, 30.0);
            enemy.setFill(Color.RED);
        } else if (lives == 2) {
            enemy.getPoints().addAll(0.0, 0.0, 20.0, 0.0, 10.0, 20.0);
            enemy.setFill(Color.DARKRED);
        }
        myGridPane.add(enemy, col, row);
    }

    private void addHero(int col, int row) {
        Polygon hero = new Polygon();
        hero.getPoints().addAll(15.0, 0.0, 30.0, 30.0, 0.0, 30.0);
        hero.setFill(Color.YELLOW);
        myGridPane.add(hero, col, row);
    }
    private void addStone(int col, int row) {
        Rectangle stone = new Rectangle(45, 45, Color.GRAY);
        myGridPane.add(stone, col, row);
    }
    private void addTree(int col, int row) {
        Circle tree = new Circle(22.5, Color.GREEN);
        myGridPane.add(tree, col, row);
    }
}
