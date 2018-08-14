package com.eunmin.v2;

import java.util.StringJoiner;

public class InlineFragment implements Selection {
    private String type;
    private Directives directives;
    private SelectionSet selectionSet;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        sj.add("...");
        if (type != null) {
            sj.add("on");
            sj.add(type);
        }
        if (directives != null) {
            sj.add(directives.toString());
        }
        sj.add(selectionSet.toString());
        return sj.toString();
    }
}
