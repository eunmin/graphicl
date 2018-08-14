package com.eunmin.v2.builder;

import com.eunmin.v2.*;

public class FieldBuilder {
    private String alias;
    private String name;
    private Arguments args;
    private Directives directives;
    private SelectionSet selectionSet;

    public static FieldBuilder create(String name) {
        FieldBuilder fb = new FieldBuilder();
        fb.name = name;
        return fb;
    }

    public FieldBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public FieldBuilder arg(String name, Object value) {
        if (args == null) {
            args = new Arguments();
        }
        args.put(name, value);
        return this;
    }

    public FieldBuilder include(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments includeArgs = new Arguments();
        includeArgs.put("if", value);
        directives.put("include", includeArgs);
        return this;
    }

    public FieldBuilder skip(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments skipArgs = new Arguments();
        skipArgs.put("if", value);
        directives.put("skip", skipArgs);
        return this;
    }

    public FieldBuilder select(Selection selection) {
        if (selectionSet == null) {
            selectionSet = new SelectionSet();
        }
        selectionSet.add(selection);
        return this;
    }

    public Field build() {
        Field field = new Field(name);
        field.setAlias(alias);
        field.setArgs(args);
        field.setDirectives(directives);
        field.setSelectionSet(selectionSet);
        return field;
    }
}
