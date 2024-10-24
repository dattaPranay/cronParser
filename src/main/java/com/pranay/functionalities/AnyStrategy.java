package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.enums.Expression;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnyStrategy implements ParsingStrategy {

    @Override
    public boolean matches(String cmd) {
        return cmd.contains(Expression.ANY.getValue());
    }

    @Override
    public Set<Integer> getParsingValues(Field field, String cmd) {
        return Arrays.stream(IntStream.range(field.getMin(), field.getMax() + 1).toArray()).boxed().collect(
                Collectors.toSet());
    }
}
