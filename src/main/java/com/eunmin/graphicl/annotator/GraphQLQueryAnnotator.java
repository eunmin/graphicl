package com.eunmin.graphicl.annotator;

import com.eunmin.graphicl.OperationDefinition;
import com.eunmin.graphicl.OperationType;
import com.eunmin.graphicl.SelectionSet;
import com.eunmin.graphicl.annotation.GraphQLField;
import com.eunmin.graphicl.annotation.GraphQLQuery;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class GraphQLQueryAnnotator {
    public static OperationDefinition toGraphQL(Class klass) {
        Annotation ann = klass.getAnnotation(GraphQLQuery.class);
        if (ann == null) {
            return null;
        }
        OperationDefinition op = new OperationDefinition();
        op.setType(OperationType.Query);

        SelectionSet selectionSet = null;
        for (Field field : klass.getDeclaredFields()) {
            if (field.getAnnotation(GraphQLField.class) != null) {
                if(op.getSelectionSet() == null) {
                    selectionSet = new SelectionSet();
                }
                selectionSet.add(GraphQLFieldAnnotator.toGraphQL(field));
            }
        }
        op.setSelectionSet(selectionSet);
        return op;
    }
}
