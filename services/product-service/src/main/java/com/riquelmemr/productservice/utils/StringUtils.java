package com.riquelmemr.productservice.utils;

import java.util.Set;
import java.util.stream.Collectors;

public class StringUtils {

    public static Set<String> convertLongToString(Set<Long> longs) {
        return longs
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());
    }
}
