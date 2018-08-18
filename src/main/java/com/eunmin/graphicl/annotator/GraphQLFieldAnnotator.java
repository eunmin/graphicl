package com.eunmin.graphicl.annotator;

import com.eunmin.graphicl.Selection;
import com.eunmin.graphicl.SelectionSet;
import com.eunmin.graphicl.annotation.GraphQLField;

import java.lang.reflect.Field;

public class GraphQLFieldAnnotator {
    public static Selection toGraphQL(Field field) {
        Class klass = field.getType();
        com.eunmin.graphicl.Field gf = new com.eunmin.graphicl.Field(field.getName());
        for (Field f : klass.getDeclaredFields()) {
            if (f.getAnnotation(GraphQLField.class) != null) {
                if(gf.getSelectionSet() == null) {
                    gf.setSelectionSet(new SelectionSet());
                }
                Selection s = GraphQLFieldAnnotator.toGraphQL(f);
                if (s != null) {
                    gf.getSelectionSet().add(s);
                }
            }
        }
        return gf;
    }
}
