package com.pranay.functionalities;

import com.pranay.Fields.Field;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnyStrategyTest {

    private AnyStrategy anyStrategy;
    private Field mockField;

    @Before
    public void setUp() {
        anyStrategy = new AnyStrategy();
        mockField = new Field() {
            @Override
            public int getMin() {
                return 1;
            }

            @Override
            public String parse(String str) {
                return null;
            }

            @Override
            public int getMax() {
                return 31;
            }
        };
    }

    @Test
    public void matches_should_returnTrue_when_commandContainsAnyExpression() {
        // given
        String validAnyCommand = "*";

        // when
        boolean result = anyStrategy.matches(validAnyCommand);

        // then
        assertTrue(result);
    }

    @Test
    public void matches_should_returnFalse_when_commandDoesNotContainAnyExpression() {
        // given
        String nonAnyCommand = "10-20";

        // when
        boolean result = anyStrategy.matches(nonAnyCommand);

        // then
        assertFalse(result);
    }

    // Test for parsing the ANY expression correctly
    @Test
    public void parse_should_returnFullRangeOfValues_when_anyExpressionProvided() {
        // given
        String anyCommand = "*"; // ANY expression

        // when
        Set<Integer> result = anyStrategy.getParsingValues(mockField, anyCommand);

        // then
        assertEquals(IntStream.rangeClosed(1, 31).boxed().collect(Collectors.toSet()), result);
    }
}
