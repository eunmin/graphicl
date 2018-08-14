package com.eunmin.v2.builder;

import com.eunmin.v2.*;

public class FragmentDefinitionBuilder {
    private String name;
    private String type;
    private Directives directives;
    private SelectionSet selectionSet;

    public static FragmentDefinitionBuilder create(String name) {
        FragmentDefinitionBuilder fdb = new FragmentDefinitionBuilder();
        fdb.name = name;
        return fdb;
    }

    public FragmentDefinitionBuilder on(String type) {
        this.type = type;
        return this;
    }

    public FragmentDefinitionBuilder include(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments includeArgs = new Arguments();
        includeArgs.put("if", value);
        directives.put("include", includeArgs);
        return this;
    }

    public FragmentDefinitionBuilder skip(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments skipArgs = new Arguments();
        skipArgs.put("if", value);
        directives.put("skip", skipArgs);
        return this;
    }

    public FragmentDefinitionBuilder select(Selection selection) {
        if (selectionSet == null) {
            selectionSet = new SelectionSet();
        }
        selectionSet.add(selection);
        return this;
    }

    public FragmentDefinition build() {
        FragmentDefinition fd = new FragmentDefinition(name, type);
        fd.setDirectives(directives);
        fd.setSelectionSet(selectionSet);
        return fd;
    }
}
