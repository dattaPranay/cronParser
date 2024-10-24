package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.enums.Expression;
import com.pranay.exceptions.InvalidExpressionException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RangeParsingStrategy implements ParsingStrategy {

    @Override
    public boolean matches(String expression) {
        return expression.contains(Expression.RANGE.getValue());
    }

    @Override
    public Set<Integer> getParsingValues(Field field, String expression) {
        String[] tokens = expression.split(Expression.RANGE.getValue());

        if (tokens.length != 2 || tokens[0].isEmpty() || tokens[1].isEmpty()) {
            throw new InvalidExpressionException(
                    "Invalid range expression: '" + expression + "'. Expected format 'start-end'.");
        }

        int startValue;
        int endValue;
        try {
            startValue = Integer.parseInt(tokens[0]);
            endValue = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            throw new InvalidExpressionException(
                    "Non-integer value found in range expression: '" + expression + "'.", e);
        }

        if (endValue < field.getMin() || endValue > field.getMax()) {
            throw new InvalidExpressionException(
                    "End value '" + endValue + "' is outside the valid range (" +
                            field.getMin() + "-" + field.getMax() + ").");
        }

        if (startValue > endValue) {
            throw new InvalidExpressionException(
                    "Start value '" + startValue + "' cannot be greater than end value '" + endValue + "'.");
        }

        if (startValue < field.getMin() || startValue > field.getMax()) {
            throw new InvalidExpressionException(
                    "Start value '" + startValue + "' is outside the valid range (" +
                            field.getMin() + "-" + field.getMax() + ").");
        }


        return IntStream.rangeClosed(startValue, endValue).boxed()
                .collect(Collectors.toSet());
    }
}
