package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.exceptions.InvalidExpressionException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;


public class NumberParsingStrategyTest {

    private NumberParsingStrategy uut;
    private Field mockField;

    @Before
    public void setUp() {
        uut = new NumberParsingStrategy();
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
    public void parse_should_returnSetOfValues_when_validNumberProvided() {
        // given
        String validNumber = "15";

        // when
        Set<Integer> result = uut.getParsingValues(mockField, validNumber);

        // then
        assertEquals(Set.of(15), result);
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_nonIntegerValueProvided() {
        // given
        String nonIntegerValue = "A15";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, nonIntegerValue);
        });

        // then
        assertEquals("Invalid number format in expression: 'A15'.", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_valueIsOutsideFieldRange() {
        // given
        String outsideRangeValue = "35";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, outsideRangeValue);
        });

        // then
        assertEquals("Value '35' is outside the valid range (1-31).", exception.getMessage());
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_valueIsBelowFieldMinRange() {
        // given
        String belowMinValue = "0";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.getParsingValues(mockField, belowMinValue);
        });

        // then
        assertEquals("Value '0' is outside the valid range (1-31).", exception.getMessage());
    }

    @Test
    public void matches_should_returnTrue_when_commandIsAllDigits() {
        // given
        String validDigits = "12345";

        // when
        boolean result = uut.matches(validDigits);

        // then
        assertTrue(result);
    }

    @Test
    public void matches_should_returnFalse_when_commandContainsNonDigits() {
        // given
        String nonDigits = "123A";

        // when
        boolean result = uut.matches(nonDigits);

        // then
        assertFalse(result);
    }
}
