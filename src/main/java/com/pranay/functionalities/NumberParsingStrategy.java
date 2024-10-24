package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.exceptions.InvalidExpressionException;

import java.util.Set;

public class NumberParsingStrategy implements ParsingStrategy {

    @Override
    public boolean matches(String command) {
        return command.chars().allMatch(Character::isDigit);
    }

    @Override
    public Set<Integer> getParsingValues(Field field, String command) {
        int parsedValue;
        try {
            parsedValue = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            throw new InvalidExpressionException(
                    "Invalid number format in expression: '" + command + "'.", e);
        }


        if (parsedValue < field.getMin() || parsedValue > field.getMax()) {
            throw new InvalidExpressionException(
                    "Value '" + parsedValue + "' is outside the valid range (" +
                            field.getMin() + "-" + field.getMax() + ").");
        }

        return Set.of(parsedValue);
    }
}
