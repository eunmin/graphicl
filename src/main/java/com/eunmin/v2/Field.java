package com.eunmin.v2;

import java.util.StringJoiner;

public class Field implements Selection {
    private String alias;
    private String name;
    private Arguments args;
    private Directives directives;
    private SelectionSet selectionSet;

    public Field(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Arguments getArgs() {
        return args;
    }

    public void setArgs(Arguments args) {
        this.args = args;
    }

    public Directives getDirectives() {
        return directives;
    }

    public void setDirectives(Directives directives) {
        this.directives = directives;
    }

    public SelectionSet getSelectionSet() {
        return selectionSet;
    }

    public void setSelectionSet(SelectionSet selectionSet) {
        this.selectionSet = selectionSet;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        if (alias != null) {
            sj.add(alias.toString() + ":");
        }
        sj.add(name.toString());
        if (args != null) {
            sj.add(args.toString());
        }
        if (directives != null) {
            sj.add(directives.toString());
        }
        if (selectionSet != null) {
            sj.add(selectionSet.toString());
        }
        return sj.toString();
    }
}
