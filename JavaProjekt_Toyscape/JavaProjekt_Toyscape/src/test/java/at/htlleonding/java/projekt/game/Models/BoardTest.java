package at.htlleonding.java.projekt.game.Models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testBoardInitialization() {
        int[][] boardArray = board.getBoard();

        assertEquals(3, boardArray[5][5]);

        int stoneCount = 0;
        int treeCount = 0;
        int enemyOneLifeCount = 0;
        int enemyTwoLifeCount = 0;

        for (int[] row : boardArray) {
            for (int cell : row) {
                switch (cell) {
                    case 4:
                        stoneCount++;
                        break;
                    case 5:
                        treeCount++;
                        break;
                    case 1:
                        enemyOneLifeCount++;
                        break;
                    case 2:
                        enemyTwoLifeCount++;
                        break;
                }
            }
        }

        assertEquals(8, stoneCount);
        assertEquals(8, treeCount);
        assertEquals(10, enemyOneLifeCount);
        assertEquals(3, enemyTwoLifeCount);

        board.printBoard();
    }

    @Test
    public void testTabuFields() {
        int[][] boardArray = board.getBoard();

        for (int[] tabuField : Board.getTabuFields()) {
            int cellValue = boardArray[tabuField[0]][tabuField[1]];
            assertTrue(cellValue == 0 || cellValue == 3);
        }
    }

    @Test
    public void testNoOverlappingObjects() {
        int[][] boardArray = board.getBoard();

        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                if (boardArray[i][j] != 0) {
                    int count = 0;
                    switch (boardArray[i][j]) {
                        case 3:
                        case 4:
                        case 5:
                        case 1:
                        case 2:
                            count++;
                            break;
                    }
                    assertTrue("Multiple objects in the same position detected", count == 1);
                }
            }
        }
    }
}