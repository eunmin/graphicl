package com.eunmin.graphicl.annotation;

import com.eunmin.graphicl.annotator.GraphQLQueryAnnotator;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {
    class Hero {
        @GraphQLField
        String name;
        @GraphQLField
        String appearsIn;
    }

    @GraphQLQuery
    class Query {
        @GraphQLField
        Hero hero;
    }

    @Test
    public void test() {
        Assert.assertEquals(
                "query { hero { name appearsIn } }",
                GraphQLQueryAnnotator.toGraphQL(Query.class).toString());
    }
}
