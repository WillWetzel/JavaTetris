package Models;

import Engines.TetrisGameEngine;
import Host.TetrisPanel;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TetrisBoardTest {

    @Test
    void getBoardSquareTest() {
        // Arrange
        TetrisBoard board = new TetrisBoard();
        board.setBoardSquare(1, 1, TetrominoeEnum.SShape);

        // Act
        TetrominoeEnum actualSquareValue = board.getBoardSquare(1, 1);

        // Assert
        assertEquals(TetrominoeEnum.SShape, actualSquareValue);
    }

    @Test
    void setBoardSquareTest() {
        // Arrange
        TetrisBoard board = new TetrisBoard();
        board.clearBoard();

        // Act
        board.setBoardSquare(1, 1, TetrominoeEnum.LineShape);
        TetrominoeEnum[] endBoard = board.getBoard();

        // Assert
        assertTrue(Arrays.stream(endBoard).anyMatch(x -> x != TetrominoeEnum.NoShape));
        assertTrue(endBoard[11] == TetrominoeEnum.LineShape);
    }

    @Test
    void squareWidthTest() {
        // Arrange
        int frameWidth = 200;
        TetrisBoard board = new TetrisBoard();
        int expectedWidth = frameWidth / board.getBoardWidth();

        // Act
        int actualWidth = board.squareWidth(frameWidth);

        // Assert
        assertEquals(expectedWidth, actualWidth);
    }

    @Test
    void squareHeightTest() {
        // Arrange
        int frameHeight = 400;
        TetrisBoard board = new TetrisBoard();
        int expectedHeight = frameHeight / board.getBoardHeight();

        // Act
        int actualHeight = board.squareHeight(frameHeight);

        // Assert
        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    void clearBoardRemovesShapesTest() throws CloneNotSupportedException {
        // Arrange
        TetrisGameEngine gameEngine = initializeGameEngine();
        TetrisBoard preStartBoard = (TetrisBoard) gameEngine.getGameBoard().clone();

        // Act
        gameEngine.start();
        gameEngine.dropDown();
        await().atLeast(5, TimeUnit.SECONDS).and().atMost(10, TimeUnit.SECONDS);
        gameEngine.pause();
        TetrisBoard boardWithShapes = (TetrisBoard) preStartBoard.clone();

        TetrisBoard endBoard = (TetrisBoard) boardWithShapes.clone();
        endBoard.clearBoard();

        // Assert
        assertTrue(Arrays.stream(preStartBoard.getBoard()).allMatch(x -> x == null));
        assertTrue(Arrays.stream(endBoard.getBoard()).allMatch(x -> x == TetrominoeEnum.NoShape));
        assertNotEquals(boardWithShapes.getBoard(), endBoard.getBoard());
    }

    @Test
    void removeBoardLine() throws CloneNotSupportedException {
        // Arrange
        TetrisBoard gameBoard = new TetrisBoard(); // shallow copy
        gameBoard.clearBoard();
        for (int xPos = 0; xPos < 10; xPos++)
        {
            gameBoard.setBoardSquare(xPos, 0, TetrominoeEnum.LineShape);
        }

        TetrisBoard boardWithFullLine = (TetrisBoard) gameBoard.clone(); // deep copy with unlinked fields.

        // Act
        gameBoard.removeBoardLine(0);

        // Assert
        assertTrue(Arrays.stream(boardWithFullLine.getBoard()).anyMatch(x -> x == TetrominoeEnum.LineShape));
        assertTrue(Arrays.stream(gameBoard.getBoard()).allMatch(x -> x == TetrominoeEnum.NoShape));
    }

    private TetrisGameEngine initializeGameEngine() {
        TetrisPanel tetrisPanel = mock(TetrisPanel.class);
        when(tetrisPanel.getStatusBar()).thenReturn(new JLabel("0"));
        return new TetrisGameEngine(tetrisPanel);
    }
}