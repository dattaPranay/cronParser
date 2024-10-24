package com.pranay.functionalities;

import com.pranay.Fields.Field;
import com.pranay.Fields.FieldImpl;

import java.util.Set;

public interface ParsingStrategy {
    boolean matches(String cmd);
    Set<Integer> getParsingValues(Field field, String cmd);
}
