package Engines;

import Host.TetrisPanel;
import Models.TetrominoeShape;
import Models.TetrisBoard;
import Models.TetrominoeEnum;
import Utilities.TetrisKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisGameEngine extends JPanel {
    private final int PERIOD_INTERVAL = 300;
    public TetrominoeShape currentPiece;
    public boolean isPaused = false;
    public int currentX = 0;
    public int currentY = 0;

    private TetrisBoard board;
    private boolean isFallingFinished = false;
    private int linesRemovedCount = 0;
    private Timer timer;
    private JLabel statusBar;

    public TetrisBoard getGameBoard() {
        return this.board;
    }

    public TetrisGameEngine(TetrisPanel parent) {
        innitGame(parent);
    }

    public void dropDown() {
        int newY = currentY;

        while (newY > 0) {
            if (!tryMove(currentPiece, currentX, newY - 1)) {
                break;
            }

            newY--;
        }

        pieceDropped();
    }

    public void start() {
        currentPiece = new TetrominoeShape();

        board.clearBoard();
        newPiece();

        timer = new Timer(PERIOD_INTERVAL, new GameCycle());
        timer.start();
    }

    public void pause() {
        isPaused = !isPaused;

        if (isPaused) {
            var msg = String.format("Paused: %d", linesRemovedCount);
            statusBar.setText(msg);
        } else {
            statusBar.setText(String.valueOf(linesRemovedCount));
        }

        repaint();
    }

    public void oneLineDown() {
        if (!tryMove(currentPiece, currentX, currentY - 1)) {
            pieceDropped();
        }
    }

    public boolean tryMove(TetrominoeShape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.getX(i);
            int y = newY - newPiece.getY(i);
            if (x < 0 || x >= board.getBoardWidth() || y < 0 || y >= board.getBoardHeight()) {
                return false;
            }

            if (board.getBoardSquare(x, y) != TetrominoeEnum.NoShape) {
                return false;
            }
        }

        currentPiece = newPiece;
        currentX = newX;
        currentY = newY;

        repaint();

        return true;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    protected void doGameCycle() {
        if (this.isPaused) {
            return;
        }

        if (this.isFallingFinished) {
            this.isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
        }

        repaint();
    }

    private void innitGame(TetrisPanel parent) {
        board = new TetrisBoard();
        setFocusable(true);
        statusBar = parent.getStatusBar();
        addKeyListener(new TetrisKeyAdapter(this));
    }

    private void drawSquare(Graphics g, int x, int y, TetrominoeEnum shapeType) {
        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        var color = colors[shapeType.ordinal()];
        var size = getSize();

        g.setColor(color);
        g.fillRect(x + 1, y +  1, board.squareWidth((int) size.getWidth()) - 2, board.squareHeight((int) size.getHeight()) - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + board.squareHeight((int) size.getHeight()) - 1, x, y);
        g.drawLine(x, y, x + board.squareWidth((int) size.getWidth()) - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + board.squareHeight((int) size.getHeight()) - 1, x + board.squareWidth((int) size.getWidth()) - 1, y + board.squareHeight((int) size.getHeight()) - 1);
        g.drawLine(x + board.squareWidth((int) size.getWidth()) - 1, y + board.squareHeight((int) size.getHeight()) - 1, x + board.squareWidth((int)size.getWidth()) - 1, y + 1);
    }

    private void doDrawing(Graphics g) {
        var size = getSize();
        int boardTop = (int) size.getHeight() - board.getBoardHeight()  * board.squareHeight((int) size.getHeight());

        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                TetrominoeEnum shape = board.getBoardSquare(j, board.getBoardHeight() - i - 1);
                if (shape != TetrominoeEnum.NoShape) {
                    drawSquare(g, j * board.squareWidth((int) size.getWidth()), boardTop + i * board.squareHeight((int) size.getHeight()), shape);
                }
            }
        }

        if (currentPiece.getTetrominoeShape() != TetrominoeEnum.NoShape) {
            for (int i = 0; i < 4; i++) {
                int x = currentX + currentPiece.getX(i);
                int y = currentY - currentPiece.getY(i);

                drawSquare(g, x * board.squareWidth((int) size.getWidth()), boardTop + (board.getBoardHeight() - y - 1) * board.squareHeight((int) size.getHeight()), currentPiece.getTetrominoeShape());
            }
        }
    }

    private void newPiece() {
        currentPiece.setRandomShape();
        currentX = board.getBoardWidth() / 2 + 1;
        currentY = board.getBoardHeight() - 1 + currentPiece.minY();

        if (!tryMove(currentPiece, currentX, currentY)) {
            currentPiece.setShape(TetrominoeEnum.NoShape);
            timer.stop();

            var msg = String.format("Game over. Score: %d", linesRemovedCount);
            statusBar.setText(msg);
        }
    }

    private void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = currentX + currentPiece.getX(i);
            int y = currentY - currentPiece.getY(i);
            board.setBoardSquare(x, y, currentPiece.getTetrominoeShape());

        }

        removeFullLines();
        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void removeFullLines() {
        int fullLineCount = 0;

        for (int i = board.getBoardHeight() - 1; i >= 0; i--) {
            boolean lineIsFull = true;

            for (int j = 0; j < board.getBoardWidth(); j++) {
                if (board.getBoardSquare(j, i) == TetrominoeEnum.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                fullLineCount++;

                board.removeBoardLine(i);
            }
        }

         if (fullLineCount > 0) {
            linesRemovedCount += fullLineCount;
            statusBar.setText(String.valueOf(linesRemovedCount));
            isFallingFinished = true;
            currentPiece.setShape(TetrominoeEnum.NoShape);
        }
    }

    protected class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }
}
