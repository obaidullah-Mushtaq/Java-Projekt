package at.htlleonding.java.projekt.game.Models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class GameTest {

    @Test
    public void testGetInstance() {
        Game game1 = Game.getInstance();
        Game game2 = Game.getInstance();
        assertEquals(game1, game2);
    }

    @Test
    public void testNewRound() {
        Game game = Game.getInstance();
        int initialRound = game.getRoundCounter();
        game.newRound();
        assertEquals(initialRound + 1, game.getRoundCounter());
    }

    @Test
    public void testMoveEnemies() {
        Game game = Game.getInstance();
        int[][] originalArray = game.getBoard().getBoard();
        int[][] copiedArray = new int[originalArray.length][originalArray[0].length];
        for (int i = 0; i < originalArray.length; i++) {
            for (int j = 0; j < originalArray[i].length; j++) {
                copiedArray[i][j] = originalArray[i][j];
            }
        }
        game.newRound();
        game.getBoard().printBoard();
        assertNotEquals(originalArray, copiedArray);
    }

    @Test
    public void testAttackHero() {
        Game game = Game.getInstance();
        Hero hero = game.getHero();
        int[] enemyPosition = {game.getBoard().getHeroPosition()[0] - 1, game.getBoard().getHeroPosition()[1]};
        Enemy enemy = new Enemy(1, enemyPosition[0], enemyPosition[1]);
        int[] enemyPosition1 = {enemyPosition[0], enemyPosition[1] + 1};
        Enemy enemy1 = new Enemy(1, enemyPosition[0], enemyPosition[1] + 1);
        int[] enemyPosition2 = {enemyPosition[0], enemyPosition[1] - 1};
        Enemy enemy2 = new Enemy(1, enemyPosition[0], enemyPosition[1] - 1);
        Game.addEnemy(enemyPosition, enemy);
        Game.addEnemy(enemyPosition1, enemy1);
        Game.addEnemy(enemyPosition2, enemy2);
        assertTrue(game.attackHero());
    }

    @Test
    public void testAttackEnemy() {
        Game game = Game.getInstance();
        int[] enemyPosition = {5, 5};
        Enemy enemy = new Enemy(1, enemyPosition[0], enemyPosition[1]);
        game.addEnemy(enemyPosition, enemy);
        game.getBoard().setPosition(enemyPosition, 1);
        assertTrue(game.attackEnemy(enemyPosition[0], enemyPosition[1]));
    }

    @Test
    public void testMoveHero() {
        Game game = Game.getInstance();
        game.getBoard().printBoard();
        System.out.println("");
        int[] initialHeroPosition = game.getBoard().getHeroPosition();
        game.moveHero(initialHeroPosition[0] + 1, initialHeroPosition[1]);
        int[] newHeroPosition = game.getBoard().getHeroPosition();
        game.getBoard().printBoard();
        assertNotEquals(initialHeroPosition, newHeroPosition);
    }
}