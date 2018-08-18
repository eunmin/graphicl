package com.eunmin.graphicl.annotation;

import com.eunmin.graphicl.annotator.GraphQLQueryAnnotator;
import org.junit.Assert;
import org.junit.Test;

public class GraphQLAnnotatorTest {
    enum Unit {
        FOOT
    }

    enum Episode {
        EMPIRE, JEDI
    }

    @GraphQLQuery
    class Hero {
        @GraphQLField
        String name;
        @GraphQLField
        String appearsIn;
    }

    @GraphQLQuery
    class QueryTestField {
        @GraphQLField
        Hero hero;
    }

    @Test
    public void testField() {
        Assert.assertEquals(
                "query { hero { name appearsIn } }",
                GraphQLQueryAnnotator.toGraphQL(QueryTestField.class).toString());
    }
}
