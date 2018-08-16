package com.eunmin.v2;

import java.util.StringJoiner;
import java.util.function.Consumer;

public class OperationDefinition {
    private OperationType type;
    private String name;
    private VariableDefinitions vars;
    private Directives directives;
    private SelectionSet selectionSet;

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VariableDefinitions getVars() {
        return vars;
    }

    public void setVars(VariableDefinitions vars) {
        this.vars = vars;
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

    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        if (type != null) {
            sj.add(type.toString());
        }
        if (name != null) {
            sj.add(name.toString());
        }
        if (vars != null) {
            sj.add(vars.toString());
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
        private OperationType type;
        private String name;
        private VariableDefinitions vars;
        private Directives directives;
        private SelectionSet selectionSet = new SelectionSet();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(OperationType type) {
            this.type = type;
            return this;
        }

        public Builder var(VariableDefinition var) {
            if (vars == null) {
                vars = new VariableDefinitions();
            }
            vars.add(var);
            return this;
        }

        public Field.Builder<Builder> field() {
            return new Field.Builder<>(this, selection -> { selectionSet.add(selection); });
        }

        public OperationDefinition build() {
            OperationDefinition operationDefinition = new OperationDefinition();
            operationDefinition.setType(type);
            operationDefinition.setName(name);
            operationDefinition.setVars(vars);
            operationDefinition.setDirectives(directives);
            operationDefinition.setSelectionSet(selectionSet);
            return operationDefinition;
        }
    }
}
