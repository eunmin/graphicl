package com.eunmin.graphicl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Directives {
    private Map<String, Arguments> directives = new LinkedHashMap<>();

    public void put(String name, Arguments args) {
        directives.put(name, args);
    }

    public void include(Object value) {
        Arguments includeArgs = new Arguments();
        includeArgs.put("if", value);
        directives.put("include", includeArgs);
    }

    public void skip(Object value) {
        Arguments includeArgs = new Arguments();
        includeArgs.put("if", value);
        directives.put("skip", includeArgs);
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
