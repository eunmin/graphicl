package com.eunmin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class GraphQLField {

    private String name;
    private Map<String, Object> args = new LinkedHashMap<>();

    public GraphQLField(String name) {
        this.name = name;
    }

    public void addArg(String name, Object value) {
        args.put(name, value);
    }

    private String toGraphQLTypeString(Object object) {
        if (object instanceof String) {
            return "\"" + object + "\"";
        }
        else {
            return object.toString();
        }
    }

    public String build() {
        StringBuffer sb = new StringBuffer(name);
        if (args.isEmpty()) {
            return sb.toString();
        }
        sb.append("(");
        StringJoiner sj = new StringJoiner(", ");
        for (String name : args.keySet()) {
            sj.add(name + ": " + toGraphQLTypeString(args.get(name)));
        }
        sb.append(sj);
        sb.append(")");
        return sb.toString();
    }
}
