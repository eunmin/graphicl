package com.eunmin.v2;

import java.util.StringJoiner;
import java.util.function.Consumer;

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

    public static class Builder<T> {
        private String type;
        private Directives directives;
        private SelectionSet selectionSet;
        private T parentBuilder;
        private Consumer<InlineFragment> callback;

        public Builder() {}

        public Builder(T parentBuilder, Consumer<InlineFragment> callback) {
            this.parentBuilder = parentBuilder;
            this.callback = callback;
        }

        public Builder<T> on(String type) {
            this.type = type;
            return this;
        }

        public Builder<T> include(Object value) {
            if (directives == null) {
                directives = new Directives();
            }
            directives.include(value);
            return this;
        }

        public Builder<T> skip(Object value) {
            if (directives == null) {
                directives = new Directives();
            }
            directives.skip(value);
            return this;
        }

        public Field.Builder<Builder<T>> field() {
            return new Field.Builder<>(this, selection -> {
                if (selectionSet == null) {
                    selectionSet = new SelectionSet();
                }
                selectionSet.add(selection);
            });
        }

        public T end() {
            callback.accept(build());
            return parentBuilder;
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
