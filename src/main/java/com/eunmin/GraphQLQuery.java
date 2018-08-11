package com.eunmin;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class GraphQLQuery {
    private List<GraphQLObject> objects = new ArrayList<GraphQLObject>();

    public static GraphQLQuery create() {
        return new GraphQLQuery();
    }

    public GraphQLQuery object(GraphQLObject object) {
        objects.add(object);
        return this;
    }

    public String build() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("query");
        sj.add("{");
        for (GraphQLObject object : objects) {
            sj.add(object.build());
        }
        sj.add("}");
        return sj.toString();
    }
}
