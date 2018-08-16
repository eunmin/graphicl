package com.eunmin.v2;

import java.util.StringJoiner;
import java.util.function.Consumer;

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

    public static class Builder<T> {
        private String alias;
        private String name;
        private Arguments args;
        private Directives directives;
        private SelectionSet selectionSet;
        private T parentBuilder;
        private Consumer<Field> callback;

        public Builder() {}

        public Builder(T parentBuilder, Consumer<Field> callback) {
            this.parentBuilder = parentBuilder;
            this.callback = callback;
        }

        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<T> alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder<T> arg(String name, Object value) {
            if (args == null) {
                args = new Arguments();
            }
            args.put(name, value);
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

        public Builder<Builder<T>> field() {
            Consumer<Field> f = selection -> {
                if (selectionSet == null) {
                    selectionSet = new SelectionSet();
                }
                selectionSet.add(selection);
            };
            return new Builder<>(this, f);
        }

        public FragmentSpread.Builder<Builder<T>> fragmentSpread() {
            return new FragmentSpread.Builder<>(this, selection -> {
                if (selectionSet == null) {
                    selectionSet = new SelectionSet();
                }
                selectionSet.add(selection);
            });
        }

        public InlineFragment.Builder<Builder<T>> inlineFragment() {
            return new InlineFragment.Builder<>(this, selection -> {
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
