package com.eunmin;

public class GraphQLFragmentSpread extends GraphQLField {

    public GraphQLFragmentSpread(String name) {
        super(name);
    }

    public static GraphQLField create(String name) {
        return new GraphQLFragmentSpread(name);
    }

    @Override
    public String build() {
        return "..." + super.build();
    }
}
