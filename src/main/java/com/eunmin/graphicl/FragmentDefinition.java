package com.eunmin.graphicl;

import java.util.StringJoiner;

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
            directives.include(value);
            return this;
        }

        public Builder skip(Object value) {
            if (directives == null) {
                directives = new Directives();
            }
            directives.skip(value);
            return this;
        }

        public Field.Builder<Builder> field() {
            return new Field.Builder<>(this, selection -> {
                if (selectionSet == null) {
                    selectionSet = new SelectionSet();
                }
                selectionSet.add(selection);
            });
        }

        public Field.Builder<Builder> field(String name) {
            return field().name(name);
        }

        public FragmentDefinition build() {
            FragmentDefinition fd = new FragmentDefinition(name, type);
            fd.setDirectives(directives);
            fd.setSelectionSet(selectionSet);
            return fd;
        }
    }
}
