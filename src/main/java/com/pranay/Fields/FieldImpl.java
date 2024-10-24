package com.pranay.Fields;

import com.pranay.enums.Expression;
import com.pranay.functionalities.ParsingStrategy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FieldImpl implements Field {
    private final int min, max;
    private final List<ParsingStrategy> strategies;

    public FieldImpl(int min, int max, List<ParsingStrategy> parsingStrategies) {
        this.min = min;
        this.max = max;
        this.strategies = parsingStrategies;

    }

    @Override
    public abstract String parse(String str);

    public Set<Integer> parseHelper(String str) {
        String[] cmdArr = str.split(Expression.LIST.getValue());
        Set<Integer> ret = new HashSet<>();
        for (String cmd : cmdArr) {
            for (ParsingStrategy strategy : strategies) {
                if (strategy.matches(cmd)) {
                    ret.addAll(strategy.getParsingValues(this, cmd));
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getMin() {
        return min;
    }
}
