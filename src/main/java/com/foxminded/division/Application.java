package com.foxminded.division;

import com.foxminded.division.calculator.IntegerDivisionCalculator;
import com.foxminded.division.formatter.IntegerDivisionFormatter;
import com.foxminded.division.model.DivisionResult;

public class Application {
    public static void main(String[] args) {
        int dividend = -46;
        int divisor = 2;

        IntegerDivisionCalculator calculator = new IntegerDivisionCalculator();
        IntegerDivisionFormatter formatter = new IntegerDivisionFormatter();
        DivisionResult result = calculator.calculateDivisionResult(dividend, divisor);
        System.out.println(formatter.formatDivisionResult(result));
        System.out.println(result);



    }
}

