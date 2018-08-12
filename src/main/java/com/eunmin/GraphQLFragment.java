package com.eunmin;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class GraphQLFragment {
    private String name;
    private String type;
    private List<GraphQLField> fields = new ArrayList<>();

    public GraphQLFragment(String name) {
        this.name = name;
    }

    public static GraphQLFragment create(String name) {
        return new GraphQLFragment(name);
    }

    public GraphQLFragment on(String type) {
        this.type = type;
        return this;
    }

    public GraphQLFragment field(GraphQLField field) {
        fields.add(field);
        return this;
    }

    public String build() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("fragment " + name + " on " + type);
        sj.add("{");
        for (GraphQLField field : fields) {
            sj.add(field.build());
        }
        sj.add("}");
        return sj.toString();
    }
}
