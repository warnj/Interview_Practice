package Specific_Questions;

import java.util.*;

// https://leetcode.com/problems/time-based-key-value-store
public class TimeMap {
    Map<String, TreeMap<Integer, String>> contents;

    public TimeMap() {
        contents = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        TreeMap<Integer, String> val = contents.get(key);
        if (val == null) {
            val = new TreeMap<>();
            contents.put(key, val);
        }
        val.put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        if (!contents.containsKey(key)) return "";
        TreeMap<Integer, String> val = contents.get(key);
        Map.Entry<Integer, String> r = val.floorEntry(timestamp);
        if (r == null) return "";
        return r.getValue();
    }
}
