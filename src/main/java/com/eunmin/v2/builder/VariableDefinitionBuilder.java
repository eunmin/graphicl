package com.eunmin.v2.builder;

import com.eunmin.v2.VariableDefinition;
import com.eunmin.v2.VariableName;

public class VariableDefinitionBuilder {
    private VariableName name;
    private String type;
    private Object defaultValue;

    public static VariableDefinitionBuilder create(VariableName name, String type) {
        VariableDefinitionBuilder vdb = new VariableDefinitionBuilder();
        vdb.name = name;
        vdb.type = type;
        return vdb;
    }

    public VariableDefinitionBuilder defaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public VariableDefinition build() {
        VariableDefinition vd = new VariableDefinition(name, type);
        vd.setDefaultValue(defaultValue);
        return vd;
    }

}
