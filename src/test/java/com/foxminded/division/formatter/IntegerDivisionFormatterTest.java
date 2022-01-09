package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDivisionFormatterTest {

    IntegerDivisionFormatter formatter = new IntegerDivisionFormatter();

    @Test
    void formatDivisionResult_shouldThrowException_whenDivisionResultNull() {
        assertThrows(NullPointerException.class, () -> formatter.formatDivisionResult(null));
    }

    @Test
    void formatDivisionResult_shouldPrintOneStep_whenDividendIsZero() {
        int dividend = 0;
        int divisor = 2000;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(0, 0, 0));
        divisionSteps.add(new DivisionStep(0, 0, 0));
        DivisionResult divisionResult = new DivisionResult(dividend, divisor, 0, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_0|2000" + "\n" +
                " 0|----" + "\n" +
                " -|0" + "\n" +
                " 0";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendMoreThanDivisor() {
        int dividend = 1234;
        int divisor = 100;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(123, 23, 100));
        divisionSteps.add(new DivisionStep(234, 34, 200));
        divisionSteps.add(new DivisionStep(0, 34, 0));
        DivisionResult divisionResult = new DivisionResult(dividend, divisor, 12, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_1234|100" + "\n" +
                " 100 |---" + "\n" +
                " --- |12" + "\n" +
                " _234" + "\n" +
                "  200" + "\n" +
                "  ---" + "\n" +
                "   34";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendLessThanDivisor() {
        int dividend = 123;
        int divisor = 1000;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(123, 123, 0));
        divisionSteps.add(new DivisionStep(0, 123, 0));
        DivisionResult divisionResult = new DivisionResult(dividend, divisor, 0, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_123|1000" + "\n" +
                "   0|----" + "\n" +
                "   -|0" + "\n" +
                " 123";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendLessThanZero() {
        int dividend = -1234;
        int divisor = 100;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(123, 23, 100));
        divisionSteps.add(new DivisionStep(234, 34, 200));
        divisionSteps.add(new DivisionStep(0, 34, 0));
        DivisionResult divisionResult = new DivisionResult(1234, divisor, -12, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_1234|100" + "\n" +
                " 100 |---" + "\n" +
                " --- |-12" + "\n" +
                " _234" + "\n" +
                "  200" + "\n" +
                "  ---" + "\n" +
                "   34";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDivisorIsNegative(){
        int dividend = 642;
        int divisor = -2;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(6, 0, 6));
        divisionSteps.add(new DivisionStep(4, 0, 4));
        divisionSteps.add(new DivisionStep(2, 0, 2));
        divisionSteps.add(new DivisionStep(0, 0, 0));

        DivisionResult divisionResult = new DivisionResult(dividend, divisor, -321, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_642|-2" + "\n" +
                " 6  |----" + "\n" +
                " -  |-321" + "\n" +
                " _4" + "\n" +
                "  4" + "\n" +
                "  -" + "\n" +
                "  _2" + "\n" +
                "   2" + "\n" +
                "   -" + "\n" +
                "   0";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendIsNegative(){
        int dividend = -46;
        int divisor = 2;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(4, 0, 4));
        divisionSteps.add(new DivisionStep(6, 0, 6));
        divisionSteps.add(new DivisionStep(0, 0, 0));
        DivisionResult divisionResult = new DivisionResult(dividend * -1, divisor, -23, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_46|2" + "\n" +
                " 4 |---" + "\n" +
                " - |-23" + "\n" +
                " _6" + "\n" +
                "  6" + "\n" +
                "  -" + "\n" +
                "  0" ;
        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendHasZeros() {
        int dividend = 300010003;
        int divisor = 2;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(3, 1, 2));
        divisionSteps.add(new DivisionStep(10, 0, 10));
        divisionSteps.add(new DivisionStep(10, 0, 10));
        divisionSteps.add(new DivisionStep(3, 1, 2));
        divisionSteps.add(new DivisionStep(0, 1, 0));

        DivisionResult divisionResult = new DivisionResult(dividend, divisor, 150005001, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_300010003|2" + "\n" +
                " 2        |---------" + "\n" +
                " -        |150005001" + "\n" +
                "_10" + "\n" +
                " 10" + "\n" +
                " --" + "\n" +
                "    _10" + "\n" +
                "     10" + "\n" +
                "     --" + "\n" +
                "        _3" + "\n" +
                "         2" + "\n" +
                "         -" + "\n" +
                "         1";

        assertEquals(expected, actual);
    }

    @Test
    void formatDivisionResult_shouldFormatDivisionResult_whenDividendHasManyZeros() {
        int dividend = 30000003;
        int divisor = 15;

        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();
        divisionSteps.add(new DivisionStep(30, 0, 30));
        divisionSteps.add(new DivisionStep(0, 3, 0));
        DivisionResult divisionResult = new DivisionResult(dividend, divisor, 2000000, divisionSteps);
        String actual = formatter.formatDivisionResult(divisionResult);

        String expected =
                "_30000003|15" + "\n" +
                " 30      |-------" + "\n" +
                " --      |2000000" + "\n" +
                "        3";


        assertEquals(expected, actual);
    }
}

