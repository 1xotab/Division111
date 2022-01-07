package com.foxminded.division.model;

import java.util.Objects;

public class DivisionStep {
    private final int elementaryDividend;
    private final int remainder;
    private final int divisorMultiple;

    public DivisionStep(int dividend, int remainder, int divisorMultiple) {
        this.elementaryDividend = dividend;
        this.remainder = remainder;
        this.divisorMultiple = divisorMultiple;
    }

    public int getElementaryDividend() {
        return elementaryDividend;
    }

    public int getRemainder() {
        return remainder;
    }

    public int getDivisorMultiple() {
        return divisorMultiple;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DivisionStep that = (DivisionStep) o;
        return elementaryDividend == that.elementaryDividend && remainder == that.remainder && divisorMultiple == that.divisorMultiple;
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementaryDividend, remainder, divisorMultiple);
    }

    @Override
    public String toString() {
        return "DivisionStep{" +
            "dividend=" + elementaryDividend +
            ", remainder=" + remainder +
            ", divisorMultiple=" + divisorMultiple +
            '}';
    }
}

