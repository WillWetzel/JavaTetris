package Models;

import java.util.Random;

public class TetrominoeShape {
    private TetrominoeEnum tetrominoeShape;
    private int coords[][];
    private int[][][] coordsTable;

    public TetrominoeShape() {
        innitShape();
    }

    public TetrominoeShape(TetrominoeShape oldShape) {
        this.tetrominoeShape = oldShape.tetrominoeShape;
        this.coords = oldShape.coords;
        this.coordsTable = oldShape.coordsTable;
    }

    public int getX(int index) {
        return coords[index][0];
    }

    public int getY(int index) {
        return coords[index][1];
    }

    public TetrominoeShape rotateLeft() {
        if (tetrominoeShape == TetrominoeEnum.SquareShape) {
            return this;
        }

        var result = new TetrominoeShape();
        result.tetrominoeShape = tetrominoeShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, getY(i));
            result.setY(i, -getX(i));
        }

        return result;
    }

    public TetrominoeShape rotateRight() {
        if (tetrominoeShape == TetrominoeEnum.SquareShape) {
            return this;
        }

        var result = new TetrominoeShape();
        result.tetrominoeShape = tetrominoeShape;

        for (int i = 0; i < 4; ++i) {
            result.setX(i, -getY(i));
            result.setY(i, getX(i));
        }

        return result;
    }

    public TetrominoeEnum getTetrominoeShape() {
        return tetrominoeShape;
    }

    public void setRandomShape() {
        var r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        TetrominoeEnum[] values = TetrominoeEnum.values();
        setShape(values[x]);
    }

    public int minY() {
        int m = coords[0][1];

        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][1]);
        }

        return m;
    }

    public void setShape(TetrominoeEnum shapeType) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; ++j) {
                coords[i][j] = coordsTable[shapeType.ordinal()][i][j];
            }
        }

        tetrominoeShape = shapeType;
    }

    private void setX(int index, int x) {
        coords[index][0] = x;
    }

    private void setY(int index, int y) {
        coords[index][1] = y;
    }

    private void innitShape() {
        coords = new int[4][2];

        coordsTable = new int[][][] {
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
                { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
                { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
                { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
                { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };

        setShape(TetrominoeEnum.NoShape);
    }
}
