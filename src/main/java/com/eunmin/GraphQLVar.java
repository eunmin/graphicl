package com.eunmin;

public class GraphQLVar {
    private String name;
    private String type;
    private Object defaultValue;

    public GraphQLVar(String name) {
        this.name = name;
    }

    public static GraphQLVar create(String name) {
        return new GraphQLVar(name);
    }

    public GraphQLVar type(String type) {
        this.type = type;
        return this;
    }

    public GraphQLVar defaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getName() {
        return "$" + name;
    }

    public String getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
