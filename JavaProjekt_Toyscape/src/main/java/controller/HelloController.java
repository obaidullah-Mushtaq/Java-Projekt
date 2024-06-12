package controller;

import Models.Board;
import Models.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HelloController {

    Game game;
    @FXML
    public Text rounds;
    @FXML
    public Button newRound;
    @FXML
    public Label lives;
    @FXML
    public Text actions;

    @FXML
    private GridPane myGridPane;
    private final int gridSize = 11;

    @FXML
    public void initialize() {
        game = Game.getInstance();
        myGridPane.setStyle("-fx-background-color: #8B4513;");
        myGridPane.setGridLinesVisible(true);
        actions.textProperty().bind(game.getActionCounterProperty().asString());
        rounds.textProperty().bind(game.getRoundCounterProperty().asString());
        initializeGame();
    }

    @FXML
    private void newRound() {
        if(game.won()) gamWon();
        game.newRound();
        myGridPane.getChildren().clear();
        myGridPane.setGridLinesVisible(true);
        initializeGame();
    }
    private void gamWon(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("INFORMATION");
        alert.setContentText("YOU WON");
        alert.showAndWait();
        System.exit(0);
    }

    private void gameLost() {
        lives.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("INFORMATION");
        alert.setContentText("YOU LOST");
        alert.showAndWait();
        System.exit(0);
    }

    private void initializeGame() {
        hearts();
        if (game.getHero().getLives() <= 0) gameLost();
        fillGridFromBoard(game.getBoard().getBoard());
    }

    private void hearts() {
        int livesLeft = game.getHero().getLives();
        if (livesLeft == 2) lives.setText("💟💟");
        else if (livesLeft == 1) lives.setText("💟");
    }

    public void handleKeyPress(KeyEvent event) {
        if (game.getActionCounter() > 0) {
            int[] position = game.getBoard().getHeroPosition();
            switch (event.getCode()) {
                case W:
                    game.moveHero(position[0] - 1, position[1]);
                    break;
                case A:
                    game.moveHero(position[0], position[1] - 1);
                    break;
                case S:
                    game.moveHero(position[0] + 1, position[1]);
                    break;
                case D:
                    game.moveHero(position[0], position[1] + 1);
                    break;
                default:
                    break;
            }
            initialize();
        } else {
            alertNoMoreActions();
        }
    }

    public void alertNoMoreActions() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNUNG");
        alert.setHeaderText("WARNUNG");
        alert.setContentText("No more actions left!");
        alert.showAndWait();
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

        enemy.setOnMouseClicked(event -> handleEnemyClick(col, row));

        myGridPane.add(enemy, col, row);
    }

    private void handleEnemyClick(int col, int row) {
        if(game.getActionCounter() == 0) alertNoMoreActions();
        else {
            game.attackEnemy(row, col);
            initialize();
        }
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