package com.eunmin.v2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Arguments {
    private Map<String, Object> args = new LinkedHashMap<>();

    public void put(String name, Object value) {
        args.put(name, value);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        for (String name: args.keySet()) {
            sj.add(name + ": " + TypeString.toString(args.get(name)));
        }
        return String.format("(%s)", sj.toString());
    }
}
