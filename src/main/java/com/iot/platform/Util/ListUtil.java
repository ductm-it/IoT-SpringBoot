package com.iot.platform.Util;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {

    public final static <T> List<T> unique(List<T> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }
    
}