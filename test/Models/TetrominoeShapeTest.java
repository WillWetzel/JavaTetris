package Models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoeShapeTest {

    @Test
    void getX() {
        // Arrange
        TetrominoeShape shape = new TetrominoeShape();
        shape.setShape(TetrominoeEnum.SquareShape);

        // Act
        var x = shape.getX(1);

        // Assert
        assertEquals(1, x);
    }

    @Test
    void getY() {
        // Arrange
        TetrominoeShape shape = new TetrominoeShape();
        shape.setShape(TetrominoeEnum.SquareShape);

        // Act
        var y = shape.getY(1);

        // Assert
        assertEquals(0, y);
    }

    @Test
    void rotateLeft() {
        /*
            The shapes are represented via the co-ordinate table as set in TetrominoeShape.innitShape()
            An SShape is represented in the matrix by:
            { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 }
            Rotating left will perform the following equation
            x = y
            y = -x
            Giving us the expected co-ordinates of:
            { -1, 0 }, { 0 , 0 }, { 0, 1 }, { 1, 1 }
         */

        // Arrange
        TetrominoeShape shape = new TetrominoeShape();
        shape.setShape(TetrominoeEnum.SShape);

        int[] expectedCoordinatesOne = new int[]{
                shape.getY(0), -shape.getX(0)
        };

        int[] expectedShapeCoordinatesTwo = new int[]{
                shape.getY(1), -shape.getX(1)
        };
        int[] expectedShapeCoordinatesThree = new int[]{
                shape.getY(2), -shape.getX(2)
        };
        int[] expectedShapeCoordinatesFour = new int[]{
                shape.getY(3), -shape.getX(3)
        };

        // Act
        TetrominoeShape rotatedShape = new TetrominoeShape(shape.rotateLeft());

        // Assert
        assertTrue(Arrays.equals(expectedCoordinatesOne, new int[] { rotatedShape.getX(0), rotatedShape.getY(0) }));
        assertTrue(Arrays.equals(expectedShapeCoordinatesTwo, new int[] { rotatedShape.getX(1), rotatedShape.getY(1) }));
        assertTrue(Arrays.equals(expectedShapeCoordinatesThree, new int[] { rotatedShape.getX(2), rotatedShape.getY(2) }));
        assertTrue(Arrays.equals(expectedShapeCoordinatesFour, new int[] { rotatedShape.getX(3), rotatedShape.getY(3) }));
    }

    @Test
    void rotateRight() {
        /*
            The shapes are represented via the co-ordinate table as set in TetrominoeShape.innitShape()
            An SShape is represented in the matrix by:
            { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 }
            Rotating right will perform the following equation
            x = -y
            y = x
            Giving us the expected co-ordinates of:
            { 1, 0 }, { 0 , 0 }, { 0, -1 }, { -1, 1 }
         */

        // Arrange
        TetrominoeShape shape = new TetrominoeShape();
        shape.setShape(TetrominoeEnum.SShape);

        int[] expectedCoordinatesOne = new int[]{
                -shape.getY(0), shape.getX(0)
        };

        int[] expectedShapeCoordinatesTwo = new int[]{
                -shape.getY(1), shape.getX(1)
        };
        int[] expectedShapeCoordinatesThree = new int[]{
                -shape.getY(2), shape.getX(2)
        };
        int[] expectedShapeCoordinatesFour = new int[]{
                -shape.getY(3), shape.getX(3)
        };

        // Act
        TetrominoeShape rotatedShape = new TetrominoeShape(shape.rotateRight());

        // Assert
        assertTrue(Arrays.equals(expectedCoordinatesOne, new int[] { rotatedShape.getX(0), rotatedShape.getY(0) }));
        assertTrue(Arrays.equals(expectedShapeCoordinatesTwo, new int[] { rotatedShape.getX(1), rotatedShape.getY(1) }));
        assertTrue(Arrays.equals(expectedShapeCoordinatesThree, new int[] { rotatedShape.getX(2), rotatedShape.getY(2) }));
        assertTrue(Arrays.equals(expectedShapeCoordinatesFour, new int[] { rotatedShape.getX(3), rotatedShape.getY(3) }));
    }

    @Test
    void getTetrominoeShape() {
        // Arrange
        TetrominoeShape testShape = new TetrominoeShape();
        testShape.setShape(TetrominoeEnum.SShape);

        // Act
        TetrominoeEnum actualShape = testShape.getTetrominoeShape();

        // Assert
        assertEquals(TetrominoeEnum.SShape, actualShape);
    }

    @Test
    void setRandomShape() {
        // Arrange
        TetrominoeShape testShape = new TetrominoeShape();

        // Act
        testShape.setRandomShape();

        // Assert
        assertNotEquals(TetrominoeEnum.NoShape, testShape.getTetrominoeShape());
    }

    @Test
    void minY() {
        /*
            Using co-ords of Line shape:
            { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 }
            minY returns the smallest value of y for our shape type.
         */
        // Arrange
        TetrominoeShape shape = new TetrominoeShape();
        shape.setShape(TetrominoeEnum.LineShape);

        // Act
        int minY = shape.minY();

        // Assert
        assertEquals(-1, minY);
    }

    @Test
    void setShape() {
        // Arrange
        TetrominoeShape noShape = new TetrominoeShape();
        TetrominoeShape shape = new TetrominoeShape();

        // Act
        shape.setShape(TetrominoeEnum.LineShape);

        // Assert
        assertEquals(TetrominoeEnum.NoShape, noShape.getTetrominoeShape());
        assertEquals(TetrominoeEnum.LineShape, shape.getTetrominoeShape());
    }
}