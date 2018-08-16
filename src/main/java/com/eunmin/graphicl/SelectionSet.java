package com.eunmin.graphicl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SelectionSet {
    public List<Selection> selections = new ArrayList<>();

    public void add(Selection selection) {
        selections.add(selection);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        for (Selection selection: selections) {
            sj.add(selection.toString());
        }
        return String.format("{ %s }", sj.toString());
    }
}
