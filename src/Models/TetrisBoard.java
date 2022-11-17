package Models;

public class TetrisBoard {
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 22;

    private TetrominoeEnum[] board;

    public TetrisBoard() {
        board = new TetrominoeEnum[BOARD_WIDTH * BOARD_HEIGHT];
    }

    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    public TetrominoeEnum shapeAt(int x, int y) {
        return board[(y * BOARD_WIDTH) + x];
    }

    public int squareWidth(int frameWidth) {
        return frameWidth / BOARD_WIDTH;
    }

    public int squareHeight(int frameHeight) {
        return frameHeight / BOARD_HEIGHT;
    }

    public void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            board[i] = TetrominoeEnum.NoShape;
        }
    }

    public TetrominoeEnum[] removeBoardLine(int lineNumber) {
        for (int k = lineNumber; k < BOARD_HEIGHT - 1; k++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[(k * BOARD_WIDTH) + j] = shapeAt(j, k + 1);
            }
        }

        return board;
    }

    public TetrominoeEnum[] setBoardSquare(int positionX, int positionY, TetrominoeEnum tetrisShape) {
        board[(positionY * BOARD_WIDTH) + positionX] = tetrisShape;

        return board;
    }
}
