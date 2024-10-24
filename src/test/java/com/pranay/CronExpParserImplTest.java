package com.pranay;

import com.pranay.CronExpParserImpl;
import com.pranay.exceptions.InvalidExpressionException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

public class CronExpParserImplTest {

    CronExpParserImpl uut;

    @Before
    public void setUp() {
        uut = new CronExpParserImpl();
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_cronExpressionIsEmpty() {
        // given

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.parse("");
        });

        // then
        assertEquals("Invalid or incomplete cron command", exception.getMessage());
    }

    @Test
    public void parse_should_returnFormattedString_when_validSimpleCronExpressionIsProvided() {
        // given
        String input = "*/20 6,1 1,5 * 4-5 /usr/bin/find";
        String expectedOutput =
                "minute        0 20 40\n" +
                        "hour          1 6\n" +
                        "day of month  1 5\n" +
                        "month         1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week   4 5\n" +
                        "command       /usr/bin/find";

        // when
        String actualOutput = uut.parse(input);

        // then
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void parse_should_throwInvalidExpressionException_when_stepValueIsInvalid() {
        // given
        String input = "*/105 0 1,5 * 1-5 /usr/bin/find";

        // when
        InvalidExpressionException exception = assertThrows(InvalidExpressionException.class, () -> {
            uut.parse(input);
        });

        // then
        assertEquals("Interval value '105' must be greater than zero and within the maximum value of 59.",
                exception.getMessage());
    }




}
