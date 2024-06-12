package Models;

import java.util.*;

public class Board {
    private int[][] board = new int[11][11];
    private static Set<int[]> tabuFields = new HashSet<>();
    private static final Random random = new Random();
    private int[] heroPosition;
    private final static int stoneID = 4;
    private final static int treeID = 5;
    private final static int deadID = -1;

    public int[][] getBoard() {
        return board;
    }
    public int getIdOfPosition(int x, int y) {
        return board[x][y];
    }
    public void setPosition(int[] position, int id){
        board[position[0]][position[1]] = id;
    }
    public int[] getHeroPosition() {
        return heroPosition;
    }
    public void setHeroPosition(int x, int y) {
        this.heroPosition = new int[]{x,y};
        board[x][y] = 3;
    }
    public static Set<int[]> getTabuFields() {
        return tabuFields;
    }

    public Board() {
        fillStandartTabuFields();
        fillBoard();
    }

    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void fillBoard() {
        placeHero(this.board, 5, 5);
        placeObjects(this.board, stoneID, 8);
        placeObjects(this.board, treeID, 8);
        placeEnemies(this.board, 1, 10);
        placeEnemies(this.board, 2, 3);
    }

    private void placeHero(int[][] board, int x, int y) {
        board[x][y] = 3;
        setHeroPosition(x,y);
    }

    private void placeObjects(int[][] board, int objectID, int count) {
        for (int i = 0; i < count; i++) {
            int[] position = generateRandomPosition();
            while (isTabuField(position[0], position[1]) || board[position[0]][position[1]] != 0) {
                position = generateRandomPosition();
            }
            board[position[0]][position[1]] = objectID;
        }
    }

    private void placeEnemies(int[][] board, int lives, int count) {
        for (int i = 0; i < count; i++) {
            int[] position = generateRandomPosition();
            while (isTabuField(position[0], position[1]) || board[position[0]][position[1]] != 0) {
                position = generateRandomPosition();
            }
            Enemy enemy = new Enemy(lives, position[0], position[1]);
            Game.addEnemy(position, enemy);
           board[position[0]][position[1]] = enemy.getId();
        }
    }

    private int[] generateRandomPosition() {
        int x = random.nextInt(board.length);
        int y = random.nextInt(board[0].length);
        return new int[]{x, y};
    }

    private boolean isTabuField(int x, int y) {
        for (int[] tabu : tabuFields) {
            if (tabu[0] == x && tabu[1] == y) {
                return true;
            }
        }
        return false;
    }

    private void fillStandartTabuFields() {
        int[][] standardTabuFields = {
                {4, 4}, {4, 5}, {4, 6}, {4, 7}, {4, 3},
                {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 3},
                {6, 4}, {6, 5}, {6, 6}, {6, 7}, {6, 3},
                {7, 4}, {7, 5}, {7, 6}, {7, 7}, {7, 3},
                {3, 4}, {3, 5}, {3, 6}, {3, 7}, {3, 3}
        };
        for (int[] field : standardTabuFields) {
            tabuFields.add(field);
        }
    }
}