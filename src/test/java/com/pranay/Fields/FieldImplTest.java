package com.pranay.Fields;

import com.pranay.functionalities.ParsingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FieldImplTest {

    private FieldImpl fieldImpl;
    private ParsingStrategy mockStrategy1;
    private ParsingStrategy mockStrategy2;

    @Before
    public void setUp() {
        mockStrategy1 = mock(ParsingStrategy.class);
        mockStrategy2 = mock(ParsingStrategy.class);

        when(mockStrategy1.matches(anyString())).thenReturn(true);
        when(mockStrategy1.getParsingValues(any(), anyString())).thenReturn(Set.of(1, 2, 3));
        when(mockStrategy2.matches(anyString())).thenReturn(false);

        fieldImpl = new FieldImpl(1, 31, List.of(mockStrategy1, mockStrategy2)) {
            @Override
            public String parse(String str) {
                return "Parsed: " + str;
            }
        };
    }

    @Test
    public void parseHelper_should_returnSetOfValues_when_validCommandIsProvided() {
        // given
        String validCommand = "1-3";

        // when
        Set<Integer> result = fieldImpl.parseHelper(validCommand);

        // then
        assertEquals(Set.of(1, 2, 3), result);
        verify(mockStrategy1).matches("1-3");
        verify(mockStrategy1).getParsingValues(fieldImpl, "1-3");
        verify(mockStrategy2, never()).matches(anyString());
    }

    @Test
    public void parseHelper_should_returnCombinedSetOfValues_when_multipleCommandsProvided() {
        // given
        String multipleCommands = "1-3,5-7";
        when(mockStrategy1.getParsingValues(any(), eq("5-7"))).thenReturn(Set.of(5, 6, 7));

        // when
        Set<Integer> result = fieldImpl.parseHelper(multipleCommands);

        // then
        assertEquals(Set.of(1, 2, 3, 5, 6, 7), result);
        verify(mockStrategy1).matches("1-3");
        verify(mockStrategy1).matches("5-7");
    }

    @Test
    public void parseHelper_should_returnEmptySet_when_noStrategyMatches() {
        // given
        String unmatchedCommand = "10-12";
        when(mockStrategy1.matches(anyString())).thenReturn(false);

        // when
        Set<Integer> result = fieldImpl.parseHelper(unmatchedCommand);

        // then
        assertTrue(result.isEmpty());
        verify(mockStrategy1).matches("10-12");
        verify(mockStrategy2).matches("10-12");
    }

    @Test
    public void parseHelper_should_useFirstMatchingStrategy_when_multipleStrategiesMatch() {
        // given
        String command = "1-3";
        when(mockStrategy2.matches(anyString())).thenReturn(true);

        // when
        Set<Integer> result = fieldImpl.parseHelper(command);

        // then
        assertEquals(Set.of(1, 2, 3), result);
        verify(mockStrategy1).matches(command);
        verify(mockStrategy2, never()).getParsingValues(any(), anyString());
    }
}
