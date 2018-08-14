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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Directives directives;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder include(Object value) {
            if (directives == null) {
                directives = new Directives();
            }
            Arguments includeArgs = new Arguments();
            includeArgs.put("if", value);
            directives.put("include", includeArgs);
            return this;
        }

        public Builder skip(Object value) {
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
}
