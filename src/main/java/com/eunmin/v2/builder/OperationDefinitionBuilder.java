package com.eunmin.v2.builder;

import com.eunmin.v2.*;

public class OperationDefinitionBuilder {
    private OperationType type;
    private String name;
    private VariableDefinitions vars;
    private Directives directives;
    private SelectionSet selectionSet = new SelectionSet();

    public static OperationDefinitionBuilder create() {
        return new OperationDefinitionBuilder();
    }

    public static OperationDefinitionBuilder create(String name) {
        OperationDefinitionBuilder odp = new OperationDefinitionBuilder();
        odp.name = name;
        return odp;
    }

    public OperationDefinitionBuilder type(OperationType type) {
        this.type = type;
        return this;
    }

    public OperationDefinitionBuilder var(VariableDefinition var) {
        if (vars == null) {
            vars = new VariableDefinitions();
        }
        vars.add(var);
        return this;
    }

    public OperationDefinitionBuilder select(Selection selection) {
        selectionSet.add(selection);
        return this;
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
