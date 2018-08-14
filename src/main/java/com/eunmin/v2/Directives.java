package com.eunmin.v2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Directives {
    private Map<String, Arguments> directives = new LinkedHashMap<>();

    public void put(String name, Arguments args) {
        directives.put(name, args);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        for (String name: directives.keySet()) {
            sj.add("@" + name + directives.get(name));
        }
        return sj.toString();
    }
}
