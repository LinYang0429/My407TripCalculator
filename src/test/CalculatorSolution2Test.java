package test;

import Calculator.CalculatorSolution2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSolution2Test {
    CalculatorSolution2 calculator;

    @BeforeEach
    void setUp() {
        calculator = new CalculatorSolution2();
    }

    @Test
    public void testEmptyCase() {
        assertEquals(calculator.costOfTrip(" ", ""), -1);
    }

    @Test
    void testCase() {
        assertEquals(calculator.costOfTrip("QEW", "Highway 400"), 16.94);
    }

    @Test
    void testCaseReverse() {
        assertEquals(calculator.costOfTrip("Highway 400", "QEW"), 16.94);
    }

    @Test
    void testCaseIncludeSideline26() {
        assertEquals(calculator.costOfTrip("Salem Road", "QEW"), 26.99);
    }

    @Test
    void testWrongInput() {
        assertEquals(calculator.costOfTrip("Q", "H 400"), -1);
    }

    @Test
    void testFalseEnter1() {
        assertEquals(calculator.costOfTrip("Bramalea Road", "Highway 410"), -1);
    }

    @Test
    void testFalseEnter2() {
        assertEquals(calculator.costOfTrip("Weston Road", "Highway 400"), -1);
    }

    @Test
    void testFalseExit1() {
        assertEquals(calculator.costOfTrip("Highway 410", "Bramalea Road"), -1);
    }

    @Test
    void testFalseExit2() {
        assertEquals(calculator.costOfTrip("Jane Street", "Weston Road"), -1);
    }
}
