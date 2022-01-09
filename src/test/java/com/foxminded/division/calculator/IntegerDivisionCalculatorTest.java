package com.foxminded.division.calculator;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDivisionCalculatorTest {
    IntegerDivisionCalculator calculator = new IntegerDivisionCalculator();

    @Test
    void calculateDivisionResult_shouldThrowException_whenDivisorIsZero() {
        int dividend = 1;
        int divisor = 0;

        assertThrows(ArithmeticException.class, () -> calculator.calculateDivisionResult(dividend, divisor));
    }

    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDividendIsZero() {
        int dividend = 0;
        int divisor = 1;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(0, 0, 0));
        expectedSteps.add(new DivisionStep(0, 0, 0));

        DivisionResult expected = new DivisionResult(dividend, divisor, 0, expectedSteps);

        assertEquals(expected, actual);
    }


    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDividendMoreThanDivisor() {
        int dividend = 123;
        int divisor = 11;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(12, 1, 11));
        expectedSteps.add(new DivisionStep(13, 2, 11));
        expectedSteps.add(new DivisionStep(0, 2, 0));

        DivisionResult expected = new DivisionResult(dividend, divisor, 11, expectedSteps);

        assertEquals(expected, actual);
    }

    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDividendLessThanDivisor() {
        int dividend = 2;
        int divisor = 4;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(dividend, dividend, 0));
        expectedSteps.add(new DivisionStep(0, 2, 0));

        DivisionResult expected = new DivisionResult(dividend, divisor, 0, expectedSteps);

        assertEquals(expected, actual);
    }

    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDivisorIsNegative() {
        int dividend = 2;
        int divisor = -4;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(dividend, dividend, 0));
        expectedSteps.add(new DivisionStep(0, dividend, 0));

        DivisionResult expected = new DivisionResult(dividend, divisor, 0, expectedSteps);

        assertEquals(expected, actual);
    }

    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDividendIsNegative() {
        int dividend = -22;
        int divisor = 4;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(22, 2, 20));
        expectedSteps.add(new DivisionStep(0, 2, 0));

        DivisionResult expected = new DivisionResult(-dividend, divisor, -5, expectedSteps);

        assertEquals(expected, actual);
    }

    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDividendHasZeros() {
        int dividend = 30001003;
        int divisor = 2;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(3, 1, 2));
        expectedSteps.add(new DivisionStep(10, 0, 10));
        expectedSteps.add(new DivisionStep(10, 0, 10));
        expectedSteps.add(new DivisionStep(3, 1, 2));
        expectedSteps.add(new DivisionStep(0, 1, 0));

        DivisionResult expected = new DivisionResult(dividend, divisor, 15000501, expectedSteps);

        assertEquals(expected, actual);
    }

    @Test
    void calculateDivisionResult_shouldReturnDivisionResult_whenDividendHasManyZeros() {
        int dividend = 30000003;
        int divisor = 15;

        DivisionResult actual = calculator.calculateDivisionResult(dividend, divisor);

        ArrayList<DivisionStep> expectedSteps = new ArrayList<>();
        expectedSteps.add(new DivisionStep(30, 0, 30));
        expectedSteps.add(new DivisionStep(0, 3, 0));

        DivisionResult expected = new DivisionResult(dividend, divisor, 2000000, expectedSteps);

        assertEquals(expected, actual);
    }
}

