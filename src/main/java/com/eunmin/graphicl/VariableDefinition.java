package com.eunmin.graphicl;

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private VariableName name;
        private String type;
        private Object defaultValue;

        public Builder name(VariableName name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder defaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public VariableDefinition build() {
            VariableDefinition vd = new VariableDefinition(name, type);
            vd.setDefaultValue(defaultValue);
            return vd;
        }
    }
}
