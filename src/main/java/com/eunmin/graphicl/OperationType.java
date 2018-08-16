package com.eunmin.graphicl;

public enum OperationType {
    Query {
        @Override
        public String toString() {
            return "query";
        }
    },
    Mutation {
        @Override
        public String toString() {
            return "mutation";
        }
    },
    Subscription {
        @Override
        public String toString() {
            return "subscription";
        }
    }
}
