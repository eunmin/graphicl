package com.eunmin;

import java.util.*;

public class GraphQLObject extends GraphQLField {

    private String alias;
    private List<GraphQLField> fields = new ArrayList<>();

    public GraphQLObject(String name) {
        super(name);
    }

    public static GraphQLObject create(String name) {
        return new GraphQLObject(name);
    }

    public GraphQLObject field(GraphQLField field) {
        fields.add(field);
        return this;
    }


    public GraphQLObject arg(String name, Object value) {
        addArg(name, value);
        return this;
    }

    public GraphQLObject alias(String alias) {
        this.alias = alias;
        return this;
    }

    public GraphQLObject include(GraphQLVar var) {
        addInclude(var);
        return this;
    }

    public GraphQLObject skip(GraphQLVar var) {
        addSkip(var);
        return this;
    }

    @Override
    public String build() {
        StringJoiner sj = new StringJoiner(" ");
        if (alias != null) {
            sj.add(alias + ":");
        }
        sj.add(super.build());
        sj.add("{");
        for (GraphQLField field : fields) {
            sj.add(field.build());
        }
        sj.add("}");
        return sj.toString();
    }
}
