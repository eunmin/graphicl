package com.eunmin.v2.builder;

import com.eunmin.v2.*;

public class InlineFragmentBuilder {
    private String type;
    private Directives directives;
    private SelectionSet selectionSet;

    public static InlineFragmentBuilder create() {
        return new InlineFragmentBuilder();
    }


    public InlineFragmentBuilder on(String type) {
        this.type = type;
        return this;
    }

    public InlineFragmentBuilder include(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments includeArgs = new Arguments();
        includeArgs.put("if", value);
        directives.put("include", includeArgs);
        return this;
    }

    public InlineFragmentBuilder skip(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments skipArgs = new Arguments();
        skipArgs.put("if", value);
        directives.put("skip", skipArgs);
        return this;
    }

    public InlineFragmentBuilder select(Selection selection) {
        if (selectionSet == null) {
            selectionSet = new SelectionSet();
        }
        selectionSet.add(selection);
        return this;
    }

    public InlineFragment build() {
        InlineFragment f = new InlineFragment();
        f.setType(type);
        f.setDirectives(directives);
        f.setSelectionSet(selectionSet);
        return f;
    }
}
