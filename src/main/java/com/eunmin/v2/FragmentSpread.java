package com.eunmin.v2;

import java.util.StringJoiner;

public class FragmentSpread implements Selection {
    private String name;
    private Directives directives;

    public FragmentSpread(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directives getDirectives() {
        return directives;
    }

    public void setDirectives(Directives directives) {
        this.directives = directives;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("...");
        sj.add(name);
        if (directives != null) {
            sj.add(directives.toString());
        }
        return sj.toString();
    }
}
