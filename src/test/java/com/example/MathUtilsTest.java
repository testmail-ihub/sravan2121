import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {

    @Test
    void testAdd() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(5, mathUtils.add(2, 3));
        assertEquals(-1, mathUtils.add(2, -3));
        assertEquals(0, mathUtils.add(0, 0));
    }

    @Test
    void testSubtract() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(1, mathUtils.subtract(3, 2));
        assertEquals(5, mathUtils.subtract(2, -3));
        assertEquals(0, mathUtils.subtract(0, 0));
    }

    @Test
    void testMultiply() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(6, mathUtils.multiply(2, 3));
        assertEquals(-6, mathUtils.multiply(2, -3));
        assertEquals(0, mathUtils.multiply(0, 5));
    }

    @Test
    void testDivide() {
        MathUtils mathUtils = new MathUtils();
        assertEquals(2.5, mathUtils.divide(5, 2), 0.001);
        assertEquals(-2.0, mathUtils.divide(4, -2), 0.001);
        assertThrows(IllegalArgumentException.class, () -> mathUtils.divide(5, 0));
    }

    @Test
    void testPower() {
        AdvancedMathUtils advancedMathUtils = new AdvancedMathUtils();
        assertEquals(8.0, advancedMathUtils.power(2, 3), 0.001);
        assertEquals(1.0, advancedMathUtils.power(10, 0), 0.001);
        assertEquals(0.25, advancedMathUtils.power(2, -2), 0.001);
    }

    @Test
    void testSquareRoot() {
        AdvancedMathUtils advancedMathUtils = new AdvancedMathUtils();
        assertEquals(3.0, advancedMathUtils.squareRoot(9), 0.001);
        assertEquals(0.0, advancedMathUtils.squareRoot(0), 0.001);
        assertThrows(IllegalArgumentException.class, () -> advancedMathUtils.squareRoot(-4));
    }
}
