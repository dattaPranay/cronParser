package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.enums.Expression;
import com.pranay.exceptions.InvalidExpressionException;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntervalParsingStrategy implements ParsingStrategy {

    @Override
    public boolean matches(String command) {
        return command.contains(Expression.INTERVAL.getValue());
    }

    @Override
    public Set<Integer> getParsingValues(Field field, String command) {
        String[] tokens = command.split("/");

        if (tokens.length != 2) {
            throw new InvalidExpressionException(
                    "Invalid interval expression: '" + command + "'. Expected format 'start/interval'.");
        }

        if (tokens[0].equals("*")) {
            tokens[0] = String.valueOf(field.getMin());
        }

        int startValue;
        int intervalValue;

        try {
            startValue = Integer.parseInt(tokens[0]);
            intervalValue = Integer.parseInt(tokens[1]);
        } catch (NumberFormatException e) {
            throw new InvalidExpressionException(
                    "Non-integer value found in interval expression: '" + command + "'.", e);
        }

        if (startValue > field.getMax() || startValue < field.getMin()) {
            throw new InvalidExpressionException(
                    "Start value '" + startValue + "' is outside the allowable range of " +
                            field.getMin() + " to " + field.getMax() + ".");
        }

        if (intervalValue <= 0 || intervalValue > field.getMax()) {
            throw new InvalidExpressionException(
                    "Interval value '" + intervalValue + "' must be greater than zero and within the maximum value of " +
                            field.getMax() + ".");
        }

        return IntStream.iterate(startValue, n -> n <= field.getMax(), n -> n + intervalValue)
                .boxed()
                .collect(Collectors.toSet());
    }
}