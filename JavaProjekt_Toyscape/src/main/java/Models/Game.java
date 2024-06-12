package Models;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.*;

public class Game {
    private static Game instance;
    private static HashMap<ArrayWrapper, Enemy> enemys = new HashMap<>();
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Board board;
    private Hero hero;
    private SimpleIntegerProperty roundCounter;
    private SimpleIntegerProperty actionCounter;

    public SimpleIntegerProperty getRoundCounterProperty() {
        return roundCounter;
    }
    public SimpleIntegerProperty getActionCounterProperty(){return actionCounter;}
    public int getRoundCounter(){return roundCounter.get();}
    public int getActionCounter(){return actionCounter.get();}

    public Hero getHero(){return hero;}
    public Board getBoard(){
        return board;
    }

    private Game(){
         this.hero = new Hero();
         this.board = new Board();
        actionCounter = new SimpleIntegerProperty(2);
        roundCounter = new SimpleIntegerProperty(0);
    }
    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }
    public void newRound(){
        resetActions();
        attackHero();
        moveEnemies();
        roundCounter.set(roundCounter.get() + 1);
        actionCounter.set(2);
    }

    private void resetActions(){
        for(Enemy enemy : enemys.values()){
            enemy.resetEnemyActions();
        }
    }

    public void moveEnemies() {
        int[][] tmpBoard = board.getBoard();
        boolean goOn = true;
        while (goOn){
            goOn = false;
            for (int i = 0; i < tmpBoard.length; i++) {
                for (int j = 0; j < tmpBoard[i].length; j++) {
                    if (board.getIdOfPosition(i, j) == 1 || board.getIdOfPosition(i, j) == 2) {
                        Enemy enemy = enemys.get(new ArrayWrapper(new int[]{i, j}));
                        if (enemy.getAction() > 0) {
                            List<int[]> shortestPath = calculateShortestPath(board, new int[]{i, j});

                            if (!shortestPath.isEmpty()) {
                                int[] nextMove = shortestPath.get(0);
                                int nextRow = nextMove[0];
                                int nextCol = nextMove[1];
                                int[] positionNext = {nextRow, nextCol};
                                int[] position = {i, j};

                                if (board.getIdOfPosition(i, j) == 1) {
                                    board.setPosition(positionNext, 1);
                                    enemys.remove(new ArrayWrapper(position));
                                    enemys.put(new ArrayWrapper(positionNext), enemy);
                                    enemy.setPosition(positionNext);
                                    enemy.setAction();
                                } else {
                                    board.setPosition(positionNext, 2);
                                        enemys.remove(new ArrayWrapper(position));
                                        enemys.put(new ArrayWrapper(positionNext), enemy);
                                        enemy.setPosition(positionNext);
                                        enemy.setAction();
                                    if (enemyInAttackRange(enemy.getPosition())) {
                                        attackHero();
                                    }
                                }
                                board.setPosition(position, 0);
                                goOn = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<int[]> calculateShortestPath(Board board, int[] start) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        pq.offer(new Node(start[0], start[1], 0, heuristic(start[0], start[1], board)));

        Set<String> visited = new HashSet<>();
        visited.add(start[0] + "," + start[1]);

        Map<String, String> parent = new HashMap<>();

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int currRow = curr.row;
            int currCol = curr.col;

            if (Arrays.equals(board.getHeroPosition(), new int[]{currRow, currCol})) {
                return constructPath(parent, start, new int[]{currRow, currCol});
            }

            for (int[] dir : DIRECTIONS) {
                int newRow = currRow + dir[0];
                int newCol = currCol + dir[1];

                boolean a = isValid(newRow, newCol, board);
                if (isValid(newRow, newCol, board) && !visited.contains(newRow + "," + newCol)) {
                    visited.add(newRow + "," + newCol);
                    pq.offer(new Node(newRow, newCol, curr.cost + 1, heuristic(newRow, newCol, board)));
                    parent.put(newRow + "," + newCol, currRow + "," + currCol);
                }
            }
        }

        return new ArrayList<>();
    }

    public static List<int[]> constructPath(Map<String, String> parent, int[] start, int[] end) {
        List<int[]> path = new ArrayList<>();
        String curr = end[0] + "," + end[1];
        while (!curr.equals(start[0] + "," + start[1])) {
            String[] parts = curr.split(",");
            path.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            curr = parent.get(curr);
        }
        Collections.reverse(path);
        return path;
    }

    public static int heuristic(int row, int col, Board board) {
        int heroRow = board.getHeroPosition()[0], heroCol = board.getHeroPosition()[1];
        return Math.abs(row - heroRow) + Math.abs(col - heroCol);
    }

    public static boolean isValid(int row, int col, Board board) {
        int[][] tmpBoard = board.getBoard();
        return row >= 0 && row < tmpBoard.length && col >= 0 && col < tmpBoard[0].length
                && board.getIdOfPosition(row, col) != 4 && board.getIdOfPosition(row, col)!= 5 && board.getIdOfPosition(row, col) != 1 && board.getIdOfPosition(row, col) != 2;
    }

    public boolean enemyInAttackRange(int[] position){
        for(int x = position[0] - 1; x <= position[0] + 1; x++){
            for(int y = position[1] - 1; y <= position[1] + 1; y++){
                int[] tmpPosition = {x, y};
                if(Arrays.equals(tmpPosition, board.getHeroPosition())) return true;
            }
        }
        return false;
    }

    public boolean attackHero(){
        for(Enemy enemy: enemys.values()){
            if(enemyInAttackRange(enemy.getPosition())){
                while(enemy.getAction() > 0) {
                    enemy.setAction();
                    boolean dead = hero.getAttacked();
                    if (dead) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean won(){
        return enemys.isEmpty();
    }
    public boolean attackEnemy(int x, int y){
        int[] position = {x, y};
            if (board.getIdOfPosition(x, y) == 1 || board.getIdOfPosition(x, y) == 2) {
                if(enemyInAttackRange(position)) {
                    boolean dead = enemys.get(new ArrayWrapper(position)).getAttacked();
                    if (dead) {
                        board.setPosition(position, 0);
                        enemys.remove(new ArrayWrapper(position));
                    }
                    actionCounter.set(actionCounter.get() - 1);
                    return true;
            }
        }
        return false;
    }
    public  boolean moveHero(int x, int y){
        if(inMoveRange(x,y)){
            board.setPosition(board.getHeroPosition(), 0);
            board.setHeroPosition(x,y);
            actionCounter.set(actionCounter.get() - 1);
            return true;
        }
        return false;
    }
    private boolean inMoveRange(int x, int y){
        int[][] tmpBoard = board.getBoard();
        return x >= 0 && x < tmpBoard.length && y >= 0 && y < tmpBoard[0].length && (board.getIdOfPosition(x,y) == 0 || board.getIdOfPosition(x,y) == -1);
    }
    public static void addEnemy(int[] position, Enemy enemy){
    enemys.put(new ArrayWrapper(position), enemy);
    }
}
