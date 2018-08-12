package com.eunmin;

import java.util.*;

public class GraphQLQuery {
    private String type;
    private String name;
    private Map<String, GraphQLVar> vars = new LinkedHashMap<>();
    private List<GraphQLObject> objects = new ArrayList<>();

    public GraphQLQuery() {
        this.type = "query";
    }

    public GraphQLQuery(String name) {
        this.type = "query";
        this.name = name;
    }

    public GraphQLQuery mutation() {
        this.type = "mutation";
        return this;
    }

    public static GraphQLQuery create() {
        return new GraphQLQuery();
    }

    public static GraphQLQuery create(String name) {
        return new GraphQLQuery(name);
    }

    public GraphQLQuery object(GraphQLObject object) {
        objects.add(object);
        return this;
    }

    private String operationNameAndVars() {
        StringBuffer sb = new StringBuffer(name);
        if (vars.isEmpty()) {
            return sb.toString();
        }
        sb.append("(");
        StringJoiner sj = new StringJoiner(", ");
        for (String name : vars.keySet()) {
            GraphQLVar var = vars.get(name);
            String def = name + ": " + var.getType();
            if (var.getDefaultValue() != null) {
                def += " = " + var.getDefaultValue();
            }
            sj.add(def);
        }
        sb.append(sj);
        sb.append(")");
        return sb.toString();
    }

    public String build() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add(type);
        if (name != null) {
            sj.add(operationNameAndVars());
        }
        sj.add("{");
        for (GraphQLObject object : objects) {
            sj.add(object.build());
        }
        sj.add("}");
        return sj.toString();
    }

    public GraphQLQuery var(GraphQLVar var) {
        vars.put(var.getName(), var);
        return this;
    }
}
