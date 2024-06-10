package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HelloController {

    @FXML
    private GridPane myGridPane;

    private final int gridSize = 12;
    private Set<String> occupiedPositions = new HashSet<>();

    @FXML
    public void initialize() {
        myGridPane.setStyle("-fx-background-color: #8B4513;");  // Hintergrundfarbe des GridPane auf Holzfarbe setzen
        placeObstaclesAndEnemies();
        placeStarInCenter();
    }

    private void placeObstaclesAndEnemies() {
        Random random = new Random();

        int numberOfStones = 7;  // Anzahl der Steine
        int numberOfTrees = 7;   // Anzahl der Bäume
        int numberOfRedEnemies = 7;   // Anzahl der roten Gegner
        int numberOfBlackEnemies = 7; // Anzahl der schwarzen Gegner

        placeElements(random, numberOfStones, Color.BLUE, "stone");  // Steine
        placeElements(random, numberOfTrees, Color.rgb(0, 100, 0), "tree");  // Bäume
        placeElements(random, numberOfRedEnemies, Color.RED, "redEnemy");  // Rote Gegner
        placeElements(random, numberOfBlackEnemies, Color.BLACK, "blackEnemy");  // Schwarze Gegner
    }

    private void placeElements(Random random, int numberOfElements, Color color, String elementType) {
        for (int i = 0; i < numberOfElements; i++) {
            int row, col;
            String position;
            do {
                row = random.nextInt(gridSize);
                col = random.nextInt(gridSize);
                position = row + "," + col;
            } while (occupiedPositions.contains(position) || isInvalidPosition(row, col));

            occupiedPositions.add(position);

            switch (elementType) {
                case "tree":
                    Circle tree = new Circle(22.5, color);
                    myGridPane.add(tree, col, row);
                    break;
                case "stone":
                    // Erstellen und Hinzufügen eines blauen Quadrats für Steine
                    Rectangle stone = new Rectangle(45, 45, color);  // Quadrat (gleiche Höhe und Breite)
                    myGridPane.add(stone, col, row);
                    break;
                case "redEnemy":
                case "blackEnemy":
                    // Erstellen und Hinzufügen eines Polygons für Gegner
                    Polygon enemy = new Polygon();
                    enemy.getPoints().addAll(0.0, 0.0,
                            30.0, 0.0,
                            15.0, 30.0);
                    enemy.setFill(color);
                    myGridPane.add(enemy, col, row);
                    break;
            }
        }
    }
    private boolean isInvalidPosition(int row, int col) {
        // Überprüfe, ob die Position in der Mitte oder unten links liegt
        int centerRow = gridSize / 2;
        int centerCol = gridSize / 2;
        return (row == centerRow && col == centerCol) || (row == gridSize - 1 && col == 0);
    }


    private void placeStarInCenter() {
        Polygon star = new Polygon();
        star.getPoints().addAll(
                0.0, 15.0,
                10.0, 15.0,
                15.0, 0.0,
                20.0, 15.0,
                30.0, 15.0,
                22.5, 22.5,
                25.0, 35.0,
                15.0, 27.5,
                5.0, 35.0,
                7.5, 22.5
        );
        star.setFill(Color.GREEN);

        int centerRow = gridSize / 2;
        int centerCol = gridSize / 2;

        myGridPane.add(star, centerCol, centerRow);
        occupiedPositions.add(centerRow + "," + centerCol);
    }
}
