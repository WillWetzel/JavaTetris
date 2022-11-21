package Engines;

import Host.TetrisPanel;
import Models.Shape;
import Models.TetrisBoard;
import Models.TetrominoeEnum;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TetrisGameEngineTest {
    private TetrisPanel tetrisPanel;
    private TetrisGameEngine gameEngine;

    @Test
    void dropDownPlacesShapeInBoardMatrix() {
        // Arrange
        initializeTestBase();
        TetrisBoard gameBoard = gameEngine.getGameBoard();
        gameEngine.start();

        // Act
        gameEngine.doGameCycle();
        gameEngine.dropDown();
        gameEngine.doGameCycle();

        // Assert
        TetrisBoard updatedGameBoard = gameEngine.getGameBoard();
        boolean containsNoTetrominoe = Arrays.stream(gameBoard.getBoard()).anyMatch(x -> x == TetrominoeEnum.NoShape);
        boolean updatedBoardContainsTetrominoe = Arrays.stream(updatedGameBoard.getBoard()).anyMatch(x -> x != TetrominoeEnum.NoShape);

        assertTrue(containsNoTetrominoe);
        assertTrue(updatedBoardContainsTetrominoe);
    }

    // Should re do this test to mock up the game engine to return exact shape types
    // Too hit or miss right now depending on rand() generation.
    @Test
    void dropDownProvidesUniqueShapeTest() {
        // Arrange
        initializeTestBase();
        gameEngine.start();

        Shape oldShape = gameEngine.currentPiece;
        TetrisBoard oldBoard = gameEngine.getGameBoard();
        int expectedXDrop = gameEngine.currentX + oldShape.getX(0);
        int y = gameEngine.currentY;

        while (y > 0) {
            if (!gameEngine.tryMove(oldShape, gameEngine.currentX, y - 1)) {
                break;
            }

            y--;
        }

        int expectedYDrop = y;
        TetrominoeEnum oldShapeTypeAtDroppedCoords = oldBoard.getBoardSquare(expectedXDrop, expectedYDrop);

        // Act
        gameEngine.doGameCycle();
        gameEngine.dropDown();
        gameEngine.doGameCycle();

        // Assert
        TetrisBoard updatedBoard = gameEngine.getGameBoard();
        TetrominoeEnum updatedShapeType = updatedBoard.getBoardSquare(expectedXDrop, expectedYDrop);
        assertNotEquals(oldShapeTypeAtDroppedCoords, updatedShapeType);
    }

    @Test
    void startTest() {
        // Arrange
        initializeTestBase();

        // Act
        gameEngine.start();

        // Assert
        assertFalse(gameEngine.isPaused);
    }

    @Test
    void pauseTest() {
        // Arrange
        initializeTestBase();

        // Act
        gameEngine.start();
        gameEngine.pause();

        // Assert
        assertTrue(gameEngine.isPaused);
    }

    @Test
    void oneLineDownTest() {
        // Arrange
        initializeTestBase();
        int starterY = gameEngine.currentY;

        // Act
        gameEngine.start();
        gameEngine.dropDown();

        // Assert
        assertNotEquals(starterY, gameEngine.currentY);
    }

    @Test
    void tryMoveTest() {
        // Arrange
        initializeTestBase();

        // Act
        gameEngine.start();

        var oldPiece = gameEngine.currentPiece;
        int oldX = gameEngine.currentX;
        int oldY = gameEngine.currentY;

        gameEngine.tryMove(oldPiece, oldX - 2, oldY);

        // Assert
        assertNotEquals(oldX, gameEngine.currentX);
    }

    private void initializeTestBase() {
        tetrisPanel = mock(TetrisPanel.class);
        when(tetrisPanel.getStatusBar()).thenReturn(new JLabel("0"));

        gameEngine = new TetrisGameEngine(tetrisPanel);
    }
}