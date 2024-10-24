package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.exceptions.InvalidExpressionException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;


public class IntervalParsingStrategyTest {

    private IntervalParsingStrategy uut;
    private Field mockField;

    @Before
    public void setUp() {
        uut = new IntervalParsingStrategy();
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
                return 59;
            }
        };
    }

    @Test
    public void parse_should_returnSetOfValues_when_validIntervalExpressionProvided() {
        // given
        String validInterval = "5/10";

        // when
        Set<Integer> result = uut.getParsingValues(mockField, validInterval);

        // then
        assertEquals(Set.of(5, 15, 25, 35, 45, 55), result);
    }

    @Test
    public void parse_should_returnSetOfValues_when_startValueIsAsterisk() {
        // given
        String intervalWithAsterisk = "*/15";

        // when
        Set<Integer> result = uut.getParsingValues(mockField, intervalWithAsterisk);

        // then
        assertEquals(Set.of(1, 16, 31, 46), result);
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_invalidFormatProvided() {
        // given
        String invalidInterval = "5/";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidInterval);
        });

        // then
        assertEquals("Invalid interval expression: '5/'. Expected format 'start/interval'.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_nonIntegerValuesProvided() {
        // given
        String nonIntegerInterval = "A/5";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, nonIntegerInterval);
        });

        // then
        assertEquals("Non-integer value found in interval expression: 'A/5'.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_startValueIsOutsideFieldRange() {
        // given
        String invalidInterval = "60/5";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidInterval);
        });

        // then
        assertEquals("Start value '60' is outside the allowable range of 1 to 59.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_intervalValueIsGreaterThanMax() {
        // given
        String invalidInterval = "5/100";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidInterval);
        });

        // then
        assertEquals("Interval value '100' must be greater than zero and within the maximum value of 59.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_intervalValueIsLessThanOrEqualToZero() {
        // given
        String invalidInterval = "5/0";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidInterval);
        });

        // then
        assertEquals("Interval value '0' must be greater than zero and within the maximum value of 59.", exception.getMessage());
    }

    @Test
    public void matches_should_returnTrue_when_commandContainsIntervalExpression() {
        // given
        String validCommand = "*/15";

        // when
        boolean result = uut.matches(validCommand);

        // then
        assertTrue(result);
    }

    @Test
    public void matches_should_returnFalse_when_commandDoesNotContainIntervalExpression() {
        // given
        String invalidCommand = "10-20";

        // when
        boolean result = uut.matches(invalidCommand);

        // then
        assertFalse(result);
    }
}

