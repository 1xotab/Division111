package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

import java.util.ArrayList;
import java.util.List;

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
        int[] dividendDigits = convertNumberToDigits(divisionResult.getDividend());
        int measuredLineLength = (MINUS + divisionResult.getSteps().get(1).getElementaryDividend()).length();

        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {

            DivisionStep currentStep = divisionResult.getSteps().get(i);
            int elementaryDividend = divisionResult.getSteps().get(i).getElementaryDividend();
            ArrayList<Integer> indents = indentCalculator(divisionResult,dividendDigits);
            int currentIndent = indents.get(i-1);

            result.append(formatStep(currentStep, currentIndent));
            measuredLineLength = (SPACE.repeat(currentIndent - 2) + MINUS + elementaryDividend).length();
        }


        List<DivisionStep> steps = divisionResult.getSteps();
        int lastRemainder = divisionResult.getSteps().get(steps.size()-1).getRemainder();
        int remainderIntend = getRemainderIndent(steps,dividendDigits,lastRemainder,measuredLineLength);

        result.append(NEWLINE).append(SPACE.repeat(remainderIntend)).append(lastRemainder);

        return result.toString();
    }

    private String formatStep(DivisionStep step, int indent) {
        int dividendLength = calculateNumberLength(step.getElementaryDividend());

        return NEWLINE
            + SPACE.repeat(indent-2)
            + MINUS + step.getElementaryDividend()
            + NEWLINE
            + SPACE.repeat(indent-1+calculateNumberLength(step.getElementaryDividend())-calculateNumberLength(step.getDivisorMultiple()))
            + step.getDivisorMultiple()
            + NEWLINE
            + SPACE.repeat(indent-1)
            + DASH.repeat(dividendLength);
    }

    private int calculateNumberLength(int number) {
        return String.valueOf(Math.abs(number)).length();
    }

    private int calculateDashAmount(int quotient, int divisor) {

        int longestNumber=quotient;
        if (calculateNumberLength(divisor) >= calculateNumberLength(quotient)) {
            longestNumber=divisor;
        }
        int dashAmount = calculateNumberLength(longestNumber);

        if(longestNumber<0){
            dashAmount++;
        }

        return dashAmount;
    }
    private int additionalSpacesCalculator(int[] dividendDigits,int start){

        int counter=0;

        for(int i=start;i<dividendDigits.length;i++){

            if(dividendDigits[i]==0){
                counter++;
            }
            else i=dividendDigits.length;
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

    private ArrayList<Integer> indentCalculator(DivisionResult divisionResult,int [] dividendDigits) {

        ArrayList<Integer> arrayOfIntends = new ArrayList<>();

        int indent = 2;
        int measuredLineLength = (MINUS + divisionResult.getSteps().get(1).getElementaryDividend()).length();

        for (int i = 1; i < divisionResult.getSteps().size() - 1; i++) {

            int previousRemainder = divisionResult.getSteps().get(i - 1).getRemainder();
            int previousDivisorMultiple = divisionResult.getSteps().get(i - 1).getDivisorMultiple();
            int previousElementaryDividend = divisionResult.getSteps().get(i - 1).getElementaryDividend();
            int elementaryDividend = divisionResult.getSteps().get(i).getElementaryDividend();

            if (i != 1) {
                indent = indent + calculateNumberLength(previousElementaryDividend) - calculateNumberLength(previousDivisorMultiple);
            }
            indent = indent + calculateNumberLength(previousDivisorMultiple) - calculateNumberLength(previousRemainder);

            if (previousRemainder == 0) {
                indent = 1 + indent + additionalSpacesCalculator(dividendDigits, measuredLineLength - 1);
            }
            measuredLineLength = (SPACE.repeat(indent - 2) + MINUS + elementaryDividend).length();

            arrayOfIntends.add(indent);
        }

        return arrayOfIntends;
    }
    private int getRemainderIndent(List<DivisionStep> steps,int[] dividendDigits,int lastRemainder,int measuredLineLength){

        int lastRemainderLength = calculateNumberLength(lastRemainder);
        int remainderIntend;

        if (steps.size() == 2) {
            remainderIntend = 1 + dividendDigits.length - lastRemainderLength;
        } else
            remainderIntend = measuredLineLength - lastRemainderLength;

        return remainderIntend;
    }


}
