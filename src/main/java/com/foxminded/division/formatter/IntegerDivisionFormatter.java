package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

import java.util.ArrayList;

public class IntegerDivisionFormatter {
    private static final String SPACE = " ";
    private static final String MINUS = "_";
    private static final String NEWLINE = "\n";
    private static final String DASH = "-";
    private static final String VERTICALLINE = "|";

    public String formatDivisionResult(DivisionResult divisionResult) {
        return createDivisionHeader(divisionResult) + createDivisionBody(divisionResult);
    }

    private String createDivisionHeader(DivisionResult divisionResult) {
        int dividend = divisionResult.getDividend();
        int divisor = divisionResult.getDivisor();
        int quotient = divisionResult.getQuotient();
        int firstStepDivisorMultiple = divisionResult.getSteps().get(0).getDivisorMultiple();
        int spaceAmountToEndLine = getSpaceAmountToEndLine(divisionResult,firstStepDivisorMultiple);
        int dashAmountBelowDivisorMultiple = calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountInAnswer = calculateDashAmount(quotient, divisor);
        int indentAmountBeforeZero = getIndentAmountBeforeZero(dividend);

        if (dividend < divisor) {
            spaceAmountToEndLine = 0;
        } else {
            indentAmountBeforeZero = 1;
        }
        return MINUS + dividend + VERTICALLINE + divisor
            + NEWLINE
            + SPACE.repeat(indentAmountBeforeZero) + firstStepDivisorMultiple + SPACE.repeat(spaceAmountToEndLine) + VERTICALLINE + DASH.repeat(dashAmountInAnswer)
            + NEWLINE
            + SPACE.repeat(indentAmountBeforeZero) + DASH.repeat(dashAmountBelowDivisorMultiple) + SPACE.repeat(spaceAmountToEndLine) + VERTICALLINE + quotient;
    }

    private String createDivisionBody(DivisionResult divisionResult) {

        StringBuilder result = new StringBuilder();
        int measuredLineLength = (MINUS + divisionResult.getSteps().get(1).getElementaryDividend()).length();

        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {

            int elementaryDividend = divisionResult.getSteps().get(i).getElementaryDividend();
            ArrayList<Integer> indents = indentCalculator(divisionResult);

            DivisionStep currentStep = divisionResult.getSteps().get(i);
            int currentIndent = indents.get(i-1);

            result.append(formatStep(currentStep, currentIndent));
            measuredLineLength = (SPACE.repeat(currentIndent - 2) + MINUS + elementaryDividend).length();
        }



        int[] dividendDigits = convertNumberToDigits(divisionResult.getDividend());
        int lastStepIndex = divisionResult.getSteps().size() - 1;
        int remainderIntend;
        if (divisionResult.getSteps().size() == 2) {
            remainderIntend = 1 + dividendDigits.length - calculateNumberLength(divisionResult.getSteps().get(lastStepIndex).getRemainder());
        } else
            remainderIntend = measuredLineLength - calculateNumberLength(divisionResult.getSteps().get(lastStepIndex).getRemainder());
            result.append(NEWLINE + SPACE.repeat(remainderIntend) + divisionResult.getSteps().get(lastStepIndex).getRemainder());

        return result.toString();
    }

    private String formatStep(DivisionStep step, int indent) {
        int dividendLength = calculateNumberLength(step.getElementaryDividend());

        return NEWLINE
            + SPACE.repeat(indent-2)
            + MINUS + step.getElementaryDividend()
            + NEWLINE
            + SPACE.repeat(indent-1+sizeDifference(step.getElementaryDividend(),step.getDivisorMultiple()))
            + step.getDivisorMultiple()
            + NEWLINE
            + SPACE.repeat(indent-1)
            + DASH.repeat(dividendLength);
    }

    private int calculateNumberLength(int number) {
        return String.valueOf(Math.abs(number)).length();
    }
    private int sizeDifference(int i, int j){
        return calculateNumberLength(i)-calculateNumberLength(j);
    }

    private int calculateDashAmount(int quotient, int divisor) {
        int dashAmount;

        if (calculateNumberLength(divisor) >= calculateNumberLength(quotient)) {
            dashAmount = calculateNumberLength(divisor);

            if (divisor < 0) {
                dashAmount++;
            }
        } else {
            dashAmount = calculateNumberLength(quotient);
            if (quotient < 0) {
                dashAmount++;
            }
        }
        return dashAmount;
    }
    private int additionalSpacesCalculator(int[] dividendDigits,int start){
        int i = start;
        int counter=0;
        while (dividendDigits[i]==0){
            counter++;
            i++;
        }
        return counter;
    }

    private int[] convertNumberToDigits(int convertible) {
        return Integer.toString(convertible).chars().map(c -> c - '0').toArray();
    }

    private int getSpaceAmountToEndLine(DivisionResult divisionResult,int firstStepDivisorMultiple) {

        return calculateNumberLength(divisionResult.getDividend()) - calculateNumberLength(firstStepDivisorMultiple);
    }
    private int getIndentAmountBeforeZero(int dividend) {
        return calculateNumberLength(dividend);
    }

    private ArrayList<Integer> indentCalculator(DivisionResult divisionResult) {

        ArrayList<Integer> arrayOfIntends = new ArrayList<>();

        int indent = 2;
        int[] dividendDigits = convertNumberToDigits(divisionResult.getDividend());

        int measuredLineLength = (MINUS + divisionResult.getSteps().get(1).getElementaryDividend()).length();


        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {

            int previousRemainder = divisionResult.getSteps().get(i - 1).getRemainder();
            int previousDivisorMultiple = divisionResult.getSteps().get(i - 1).getDivisorMultiple();
            int previousElementaryDividend = divisionResult.getSteps().get(i - 1).getElementaryDividend();
            int elementaryDividend = divisionResult.getSteps().get(i).getElementaryDividend();


            if (i != 1) {
                indent = indent + sizeDifference(previousElementaryDividend, previousDivisorMultiple);
            }

            indent = indent + sizeDifference(previousDivisorMultiple, previousRemainder);


            if (previousRemainder == 0) {
                indent = 1 + indent + additionalSpacesCalculator(dividendDigits, measuredLineLength - 1);
            }

            measuredLineLength = (SPACE.repeat(indent - 2) + MINUS + elementaryDividend).length();

            arrayOfIntends.add(indent);
        }

        return arrayOfIntends;
    }


}
