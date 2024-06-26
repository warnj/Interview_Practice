package Tests;

import General_Questions.Intervals;
import Specific_Questions.Calculator;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testCalculate() {
        assertEquals(3, Calculator.calculate("3+2-2"));
        assertEquals(2, Calculator.calculate(" 1 + 1 "));
        assertEquals(23, Calculator.calculate("(1+(4+5+2)-3)+(6+8)"));
    }

    @Test
    public void testCalculate2() {
        assertEquals(7, Calculator.calculate2("3+2*2"));
        assertEquals(15, Calculator.calculate2("1+2+3+4+5"));
        assertEquals(21, Calculator.calculate2("1+2+3+4+5+6"));
        assertEquals(1, Calculator.calculate2(" 3/2 "));
        assertEquals(5, Calculator.calculate2(" 3+5 / 2 "));
    }

    @Test
    public void testCalculateSimple() {
        assertEquals(10, Calculator.calculateSimple("3+2*2"));
        assertEquals(15, Calculator.calculateSimple("1+2+3+4+5"));
        assertEquals(21, Calculator.calculateSimple("1+2+3+4+5+6"));
        assertEquals(1, Calculator.calculateSimple(" 3/2 "));
        assertEquals(4, Calculator.calculateSimple(" 3+5 / 2 "));
    }
}
