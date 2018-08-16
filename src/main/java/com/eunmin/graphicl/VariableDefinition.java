package com.eunmin.graphicl;

import java.util.function.Consumer;

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

    public static class Builder<T> {
        private VariableName name;
        private String type;
        private Object defaultValue;
        private T parentBuilder;
        private Consumer<VariableDefinition> callback;

        public Builder() {}

        public Builder(T parentBuilder, Consumer<VariableDefinition> callback) {
            this.parentBuilder = parentBuilder;
            this.callback = callback;
        }

        public Builder<T> name(VariableName name) {
            this.name = name;
            return this;
        }

        public Builder<T> type(String type) {
            this.type = type;
            return this;
        }

        public Builder<T> defaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public T end() {
            callback.accept(build());
            return parentBuilder;
        }

        public VariableDefinition build() {
            VariableDefinition vd = new VariableDefinition(name, type);
            vd.setDefaultValue(defaultValue);
            return vd;
        }
    }
}
