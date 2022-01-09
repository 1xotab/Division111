package com.foxminded.division.calculator;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;
import java.util.ArrayList;

public class IntegerDivisionCalculator {

    public DivisionResult calculateDivisionResult(int dividend, int divisor) {
        int quotient = dividend / divisor;

        dividend = Math.abs(dividend);

        int[] dividendDigits = convertNumberToDigits(dividend);
        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();

        if(dividend<divisor){
            divisionSteps.add(new DivisionStep(dividend,dividend,0));
        }

        divisionSteps.addAll(createMiddleSteps(dividendDigits,divisor));
        return new DivisionResult(dividend, divisor, quotient, divisionSteps);

    }

    private int[] convertNumberToDigits(int convertible) {
        return Integer.toString(convertible).chars().map(c -> c - '0').toArray();
    }

    private ArrayList<DivisionStep> createMiddleSteps(int[] dividendDigits, int divisor){

        int remainder = 0;
        ArrayList<DivisionStep> divisionSteps = new ArrayList<>();

        for (int i = 0;i<dividendDigits.length;i++) {

            int localDividend = dividendDigits[i];

            remainder = combineNumbers(remainder, localDividend);
            if (remainder >= divisor) {
                divisionSteps.add(buildDivisionStep(remainder, divisor));
                remainder = remainder % divisor;
            }

            if (i == dividendDigits.length - 1) {
                divisionSteps.add(new DivisionStep(0,remainder,0));
            }
        }

        return divisionSteps;
    }

    private int combineNumbers(int x, int y) {
        return x * 10 + y;

    }

    private DivisionStep buildDivisionStep(int dividend, int divisor) {
        int stepRemainder = dividend % divisor;
        int stepDivisorMultiple = dividend - stepRemainder;

        return new DivisionStep(dividend, stepRemainder, stepDivisorMultiple);
    }
}

