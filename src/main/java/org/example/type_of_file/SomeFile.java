package org.example.type_of_file;

import java.math.BigInteger;
import java.util.Map;

public interface SomeFile {
    void readAndCalculate();

    Map<String, Integer> getDuplicate();

    Map<String, BigInteger> getSumWeight();

    long getMaximum();

    long getMinimum();
}
