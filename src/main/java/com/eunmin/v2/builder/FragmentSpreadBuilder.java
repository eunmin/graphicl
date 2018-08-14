package com.eunmin.v2.builder;

import com.eunmin.v2.Arguments;
import com.eunmin.v2.Directives;
import com.eunmin.v2.FragmentSpread;

public class FragmentSpreadBuilder {
    private String name;
    private Directives directives;

    public static FragmentSpreadBuilder create(String name) {
        FragmentSpreadBuilder fsb = new FragmentSpreadBuilder();
        fsb.name = name;
        return fsb;
    }

    public FragmentSpreadBuilder include(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments includeArgs = new Arguments();
        includeArgs.put("if", value);
        directives.put("include", includeArgs);
        return this;
    }

    public FragmentSpreadBuilder skip(Object value) {
        if (directives == null) {
            directives = new Directives();
        }
        Arguments skipArgs = new Arguments();
        skipArgs.put("if", value);
        directives.put("skip", skipArgs);
        return this;
    }

    public FragmentSpread build() {
        FragmentSpread fs = new FragmentSpread(name);
        fs.setDirectives(directives);
        return fs;
    }
}
