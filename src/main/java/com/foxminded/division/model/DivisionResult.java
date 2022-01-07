package com.foxminded.division.model;

import java.util.List;
import java.util.Objects;

public class DivisionResult {
    private final int dividend;
    private final int divisor;
    private final int quotient;
    private final List<DivisionStep> steps;

    public DivisionResult(int dividend, int divisor, int quotient, List<DivisionStep> steps) {
        this.dividend = dividend;
        this.divisor = divisor;
        this.quotient = quotient;
        this.steps = steps;
    }

    public int getDividend() {
        return dividend;
    }

    public int getDivisor() {
        return divisor;
    }

    public int getQuotient() {
        return quotient;
    }

    public List<DivisionStep> getSteps() {
        return steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DivisionResult result = (DivisionResult) o;
        return dividend == result.dividend && divisor == result.divisor && quotient == result.quotient && Objects.equals(steps, result.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dividend, divisor, quotient, steps);
    }

    @Override
    public String toString() {
        return "DivisionResult{" +
            "dividend=" + dividend +
            ", divisor=" + divisor +
            ", quotient=" + quotient +
            ", divisionSteps=" + steps +
            '}';
    }
}

