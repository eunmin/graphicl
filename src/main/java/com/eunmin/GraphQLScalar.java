package com.eunmin;

public class GraphQLScalar extends GraphQLField {

    public GraphQLScalar(String name) {
        super(name);
    }

    public static GraphQLScalar create(String name) {
        return new GraphQLScalar(name);
    }

    public GraphQLScalar arg(String name, Object value) {
        addArg(name, value);
        return this;
    }
}
