package com.eunmin.graphicl;

import java.util.*;

public class VariableDefinitions {
    public List<VariableDefinition> vars = new ArrayList<>();

    public void add(VariableDefinition var) {
        vars.add(var);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        for (VariableDefinition var: vars) {
            sj.add(var.toString());
        }
        return String.format("(%s)", sj.toString());
    }
}
