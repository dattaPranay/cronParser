package com.pranay.Fields;

import com.pranay.functionalities.*;
import com.pranay.functionalities.NumberParsingStrategy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Month extends FieldImpl{
    public Month() {
        super(1, 12, List.of(new RangeParsingStrategy(), new IntervalParsingStrategy(), new NumberParsingStrategy(), new AnyStrategy()));
    }

    @Override
    public String parse(String str) {
        Set<Integer> values = parseHelper(str);
        return "month         " + values.stream().sorted().map(String::valueOf).collect(Collectors.joining(" "));
    }
}
