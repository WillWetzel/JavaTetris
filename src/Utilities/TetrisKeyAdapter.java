package Utilities;

import Engines.TetrisGameEngine;
import Models.TetrominoeEnum;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TetrisKeyAdapter extends KeyAdapter {
    protected TetrisGameEngine gameEngine;

    public TetrisKeyAdapter(TetrisGameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameEngine.currentPiece.getTetrominoeShape() == TetrominoeEnum.NoShape) {
            return;
        }

        int keycode = e.getKeyCode();

        switch (keycode) {
            case KeyEvent.VK_P -> gameEngine.pause();
            case KeyEvent.VK_LEFT -> gameEngine.tryMove(gameEngine.currentPiece, gameEngine.currentX - 1, gameEngine.currentY);
            case KeyEvent.VK_RIGHT -> gameEngine.tryMove(gameEngine.currentPiece, gameEngine.currentX + 1, gameEngine.currentY);
            case KeyEvent.VK_DOWN -> gameEngine.tryMove(gameEngine.currentPiece.rotateRight(), gameEngine.currentX, gameEngine.currentY);
            case KeyEvent.VK_UP -> gameEngine.tryMove(gameEngine.currentPiece.rotateLeft(), gameEngine.currentX, gameEngine.currentY);
            case KeyEvent.VK_SPACE -> gameEngine.dropDown();
            case KeyEvent.VK_D -> gameEngine.oneLineDown();
        }
    }
}