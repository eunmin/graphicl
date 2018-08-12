package com.eunmin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public abstract class GraphQLField {

    private String name;
    private GraphQLVar include;
    private GraphQLVar skip;
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
        else if (object instanceof GraphQLVar) {
            GraphQLVar var = (GraphQLVar)object;
            return var.getName();
        }
        else {
            return object.toString();
        }
    }

    public void addInclude(GraphQLVar var) {
        this.include = var;
    }

    public void addSkip(GraphQLVar var) {
        this.skip = var;
    }

    public String build() {
        StringBuffer sb = new StringBuffer(name);
        if (!args.isEmpty()) {
            sb.append("(");
            StringJoiner sj = new StringJoiner(", ");
            for (String name : args.keySet()) {
                sj.add(name + ": " + toGraphQLTypeString(args.get(name)));
            }
            sb.append(sj);
            sb.append(")");
        }
        if (include != null) {
            sb.append(" @include(if: " + include.getName() + ")");
        }
        if (skip != null) {
            sb.append(" @skip(if: " + skip.getName() + ")");
        }
        return sb.toString();
    }
}
