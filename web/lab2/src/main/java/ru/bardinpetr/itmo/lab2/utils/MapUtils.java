package ru.bardinpetr.itmo.lab2.utils;

import java.util.List;
import java.util.Map;

public class MapUtils {
    public static <K> boolean containsKeys(Map<K, ?> map, List<K> keys) {
        return keys.stream().allMatch(map::containsKey);
    }
}
