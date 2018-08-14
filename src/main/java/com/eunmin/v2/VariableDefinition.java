package com.eunmin.v2;

public class VariableDefinition {
    private VariableName name;
    private String type;
    private Object defaultValue;

    public VariableDefinition(VariableName name, String type) {
        this.name = name;
        this.type = type;
    }

    public VariableName getName() {
        return name;
    }

    public void setName(VariableName name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        String s = name + ": " + type;
        if (defaultValue != null) {
            s += " = " + defaultValue;
        }
        return s;
    }
}
