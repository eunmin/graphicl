package com.eunmin;

import org.junit.Assert;
import org.junit.Test;

public class GraphQLQueryTest {
    enum Unit {
        FOOT
    }

    enum Episode {
        EMPIRE, JEDI
    }

    @Test
    public void testFields() {
        Assert.assertEquals(
                "query { hero { name appearsIn } }",
                GraphQLQuery.create()
                        .object(GraphQLObject.create("hero")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLScalar.create("appearsIn")))
                        .build());
    }

    @Test
    public void testObjectField() {
        Assert.assertEquals(
                "query { hero { name friends { name } } }",
                GraphQLQuery.create()
                        .object(GraphQLObject.create("hero")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLObject.create("friends")
                                        .field(GraphQLScalar.create("name"))))
                        .build());
    }

    @Test
    public void testArguments() {
        Assert.assertEquals(
                "query { human(id: \"1000\") { name height } }",
                GraphQLQuery.create()
                        .object(GraphQLObject.create("human").arg("id", "1000")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLScalar.create("height")))
                        .build());
    }

    @Test
    public void testScalarArguments() {
        Assert.assertEquals(
                "query { human(id: \"1000\") { name height(unit: FOOT) } }",
                GraphQLQuery.create()
                        .object(GraphQLObject.create("human").arg("id", "1000")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLScalar.create("height").arg("unit", Unit.FOOT)))
                        .build());
    }

    @Test
    public void testAliases() {
        Assert.assertEquals(
                "query { empireHero: hero(episode: EMPIRE) { name } jediHero: hero(episode: JEDI) { name } }",
                GraphQLQuery.create()
                        .object(GraphQLObject.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
                                .field(GraphQLScalar.create("name")))
                        .object(GraphQLObject.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
                                .field(GraphQLScalar.create("name")))
                        .build());
    }

    @Test
    public void testFragmentSpreads() {
        Assert.assertEquals(
                "query { leftComparison: hero(episode: EMPIRE) { ...comparisonFields } rightComparison: hero(episode: JEDI) { ...comparisonFields } }",
                GraphQLQuery.create()
                        .object(GraphQLObject.create("hero").alias("leftComparison").arg("episode", Episode.EMPIRE)
                                .field(GraphQLFragmentSpread.create("comparisonFields")))
                        .object(GraphQLObject.create("hero").alias("rightComparison").arg("episode", Episode.JEDI)
                                .field(GraphQLFragmentSpread.create("comparisonFields")))
                        .build());
    }
    @Test
    public void testFragment() {
        Assert.assertEquals(
                "fragment comparisonFields on Character { name appearsIn friends { name } }",
                GraphQLFragment.create("comparisonFields").on("Character")
                        .field(GraphQLScalar.create("name"))
                        .field(GraphQLScalar.create("appearsIn"))
                        .field(GraphQLObject.create("friends")
                                    .field(GraphQLScalar.create("name")))
                        .build());
    }
}
