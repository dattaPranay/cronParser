package com.pranay.Fields;

import com.pranay.functionalities.AnyStrategy;
import com.pranay.functionalities.IntervalParsingStrategy;
import com.pranay.functionalities.NumberParsingStrategy;
import com.pranay.functionalities.RangeParsingStrategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Minute extends FieldImpl {
    public Minute() {
        super(0, 59, List.of(
                new RangeParsingStrategy(),
                new IntervalParsingStrategy(),
                new NumberParsingStrategy(),
                new AnyStrategy()));
    }
    @Override
    public String parse(String str) {
        Set<Integer> values = parseHelper(str);
        return "minute        " + values.stream().sorted().map(String::valueOf).collect(Collectors.joining(" "));
    }
}
