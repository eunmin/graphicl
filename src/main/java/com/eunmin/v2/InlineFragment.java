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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String type;
        private Directives directives;
        private SelectionSet selectionSet;

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

        public Builder select(Selection selection) {
            if (selectionSet == null) {
                selectionSet = new SelectionSet();
            }
            selectionSet.add(selection);
            return this;
        }

        public InlineFragment build() {
            InlineFragment f = new InlineFragment();
            f.setType(type);
            f.setDirectives(directives);
            f.setSelectionSet(selectionSet);
            return f;
        }
    }
}
