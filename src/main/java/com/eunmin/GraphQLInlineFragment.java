package com.eunmin;

public class GraphQLInlineFragment extends GraphQLField {

    private GraphQLFragment fragment;

    public GraphQLInlineFragment(String name) {
        super(name);
        this.fragment = GraphQLFragment.create(null);
    }

    public static GraphQLInlineFragment create() {
        return new GraphQLInlineFragment(null);
    }

    public GraphQLInlineFragment on(String type) {
        fragment.on(type);
        return this;
    }

    public GraphQLInlineFragment field(GraphQLField field) {
        fragment.field(field);
        return this;
    }

    public String build() {
        return fragment.build();
    }
}
