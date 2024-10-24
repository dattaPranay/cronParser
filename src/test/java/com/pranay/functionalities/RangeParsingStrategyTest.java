package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.exceptions.InvalidExpressionException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class RangeParsingStrategyTest {

    private RangeParsingStrategy uut;
    private Field mockField;

    @Before
    public void setUp() {
        uut = new RangeParsingStrategy();
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
    public void parse_should_returnSetOfValues_when_validRangeExpressionProvided() {
        // given
        String validRange = "5-10";

        // when
        Set<Integer> result = uut.getParsingValues(mockField, validRange);

        // then
        assertEquals(Set.of(5, 6, 7, 8, 9, 10), result);
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_invalidRangeFormatProvided() {
        // given
        String invalidRange = "5-";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidRange);
        });

        // then
        assertEquals("Invalid range expression: '5-'. Expected format 'start-end'.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_nonIntegerRangeValueProvided() {
        // given
        String nonIntegerRange = "A-10";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, nonIntegerRange);
        });

        // then
        assertEquals("Non-integer value found in range expression: 'A-10'.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_startValueIsGreaterThanEndValue() {
        // given
        String invalidRange = "15-10";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidRange);
        });

        // then
        assertEquals("Start value '15' cannot be greater than end value '10'.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_startValueIsOutsideFieldRange() {
        // given
        String invalidRange = "0-10";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidRange);
        });

        // then
        assertEquals("Start value '0' is outside the valid range (1-31).", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_endValueIsOutsideFieldRange() {
        // given
        String invalidRange = "10-35";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, invalidRange);
        });

        // then
        assertEquals("End value '35' is outside the valid range (1-31).", exception.getMessage());
    }
}
