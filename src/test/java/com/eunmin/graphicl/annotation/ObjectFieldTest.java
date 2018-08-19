package com.eunmin.graphicl.annotation;

import com.eunmin.graphicl.annotator.GraphQLQueryAnnotator;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ObjectFieldTest {
    class Friend {
        @GraphQLField
        String name;
    }

    class Hero {
        @GraphQLField
        String name;
        @GraphQLField
        List<Friend> friends;
    }

    @GraphQLQuery
    class Query {
        @GraphQLField
        Hero hero;
    }

    @Test
    public void test() {
        Assert.assertEquals(
                "query { hero { name friends { name } } }",
                GraphQLQueryAnnotator.toGraphQL(Query.class).toString());
    }
}
