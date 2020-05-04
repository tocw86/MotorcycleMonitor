package com.software4bikers.motorcyclerun.helpers;

import java.util.Arrays;
import java.util.List;

public class Helper {
    public static double median(List<Integer> values) {
         return (values.get(values.size()/2) + values.get(values.size()/2 - 1))/2;
    }
}