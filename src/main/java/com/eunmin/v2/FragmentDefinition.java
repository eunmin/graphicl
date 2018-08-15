package com.eunmin.v2;

import java.util.StringJoiner;
import java.util.function.Consumer;

public class FragmentDefinition {
    private String name;
    private String type;
    private Directives directives;
    private SelectionSet selectionSet;

    public FragmentDefinition(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        sj.add("fragment");
        sj.add(name);
        sj.add("on");
        sj.add(type);
        if (directives != null) {
            sj.add(directives.toString());
        }
        sj.add(selectionSet.toString());
        return sj.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String type;
        private Directives directives;
        private SelectionSet selectionSet;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder on(String type) {
            this.type = type;
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

        public Field.Builder<Builder> field() {
            Consumer<Field> f = selection -> {
                if (selectionSet == null) {
                    selectionSet = new SelectionSet();
                }
                selectionSet.add(selection);
            };
            return new Field.Builder<>(this, f);
        }

        public Builder select(Selection selection) {
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
}
