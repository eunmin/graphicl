package com.eunmin.graphicl;

public class GraphQL {
    public static OperationDefinition.Builder query() {
        return OperationDefinition.builder().type(OperationType.Query);
    }

    public static OperationDefinition.Builder query(String name) {
        return OperationDefinition.builder().type(OperationType.Query).name(name);
    }

    public static OperationDefinition.Builder mutation() {
        return OperationDefinition.builder().type(OperationType.Mutation);
    }

    public static OperationDefinition.Builder mutation(String name) {
        return OperationDefinition.builder().type(OperationType.Mutation).name(name);
    }

    public static OperationDefinition.Builder subscription() {
        return OperationDefinition.builder().type(OperationType.Subscription);
    }

    public static OperationDefinition.Builder subscription(String name) {
        return OperationDefinition.builder().type(OperationType.Subscription).name(name);
    }
}
