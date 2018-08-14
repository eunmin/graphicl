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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String alias;
        private String name;
        private Arguments args;
        private Directives directives;
        private SelectionSet selectionSet;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder arg(String name, Object value) {
            if (args == null) {
                args = new Arguments();
            }
            args.put(name, value);
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

        public Builder select(Selection selection) {
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
}
