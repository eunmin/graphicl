package com.eunmin.graphicl;

public class VariableName {
    private String name;

    public VariableName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "$" + name;
    }
}
