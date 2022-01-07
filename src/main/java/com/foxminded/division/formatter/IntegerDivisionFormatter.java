package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

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
        int spaceAmountToEndLine = calculateNumberLength(divisionResult.getDividend()) - calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountBelowDivisorMultiple = calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountInAnswer = calculateDashAmount(quotient, divisor);
        int indentAmountBeforeZero = calculateNumberLength(dividend);

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
        int indent = 2;

        int lastStepIndex = divisionResult.getSteps().size() - 1;
        int[] dividendDigits = convertNumberToDigits(divisionResult.getDividend());
        String measuredLine = MINUS + divisionResult.getSteps().get(1).getElementaryDividend();


        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {
            indent = indent + sizeDifference(divisionResult.getSteps().get(i - 1).getDivisorMultiple(), divisionResult.getSteps().get(i - 1).getRemainder());

            if (divisionResult.getSteps().get(i - 1).getRemainder() == 0) {
                indent++;
            }

            int lineLength = measuredLine.length();
            if (divisionResult.getSteps().get(i - 1).getRemainder() == 0) {
                indent = indent + additionalSpacesCalculator(dividendDigits, lineLength - 1);
            }
            result.append(formatStep(divisionResult.getSteps().get(i), indent));
            measuredLine = SPACE.repeat(indent - 2) + MINUS + divisionResult.getSteps().get(i).getElementaryDividend();
            indent = indent + sizeDifference(divisionResult.getSteps().get(i).getElementaryDividend(), divisionResult.getSteps().get(i).getDivisorMultiple());
        }

        int remainderIntend;
        if (divisionResult.getSteps().size() == 2) {
            remainderIntend = 1 + dividendDigits.length - calculateNumberLength(divisionResult.getSteps().get(lastStepIndex).getRemainder());
        } else
            remainderIntend = measuredLine.length() - calculateNumberLength(divisionResult.getSteps().get(lastStepIndex).getRemainder());
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
    public static int additionalSpacesCalculator(int[] dividendDigits,int start){
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
}
