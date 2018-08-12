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

    @Test
    public void testOperationName() {
        Assert.assertEquals(
                "query HeroNameAndFriends { hero { name friends { name } } }",
                GraphQLQuery.create("HeroNameAndFriends")
                        .object(GraphQLObject.create("hero")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLObject.create("friends")
                                        .field(GraphQLScalar.create("name"))))
                        .build());
    }

    @Test
    public void testVariables() {
        Assert.assertEquals(
                "query HeroNameAndFriends($episode: Episode) { hero(episode: $episode) { name friends { name } } }",
                GraphQLQuery.create("HeroNameAndFriends")
                        .var(GraphQLVar.create("episode").type("Episode"))
                        .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLObject.create("friends")
                                        .field(GraphQLScalar.create("name"))))
                        .build());
    }

    @Test
    public void testVariablesDefaultValue() {
        Assert.assertEquals(
                "query HeroNameAndFriends($episode: Episode = JEDI) { hero(episode: $episode) { name friends { name } } }",
                GraphQLQuery.create("HeroNameAndFriends")
                        .var(GraphQLVar.create("episode").type("Episode").defaultValue(Episode.JEDI))
                        .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLObject.create("friends")
                                        .field(GraphQLScalar.create("name"))))
                        .build());
    }

    @Test
    public void testIncludeDirectives() {
        Assert.assertEquals(
                "query Hero($episode: Episode, $withFriends: Boolean!) { hero(episode: $episode) { name friends @include(if: $withFriends) { name } } }",
                GraphQLQuery.create("Hero")
                        .var(GraphQLVar.create("episode").type("Episode"))
                        .var(GraphQLVar.create("withFriends").type("Boolean!"))
                        .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLObject.create("friends")
                                        .include(GraphQLVar.create("withFriends"))
                                        .field(GraphQLScalar.create("name"))))
                        .build());
    }

    @Test
    public void testSkipDirectives() {
        Assert.assertEquals(
                "query Hero($episode: Episode, $withFriends: Boolean!) { hero(episode: $episode) { name friends @skip(if: $withFriends) { name } } }",
                GraphQLQuery.create("Hero")
                        .var(GraphQLVar.create("episode").type("Episode"))
                        .var(GraphQLVar.create("withFriends").type("Boolean!"))
                        .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLObject.create("friends")
                                        .skip(GraphQLVar.create("withFriends"))
                                        .field(GraphQLScalar.create("name"))))
                        .build());
    }

    @Test
    public void testMutation() {
        Assert.assertEquals(
                "mutation CreateReviewForEpisode($ep: Episode!, $review: ReviewInput!) { createReview(episode: $ep, review: $review) { stars commentary } }",
                GraphQLQuery.create("CreateReviewForEpisode").mutation()
                        .var(GraphQLVar.create("ep").type("Episode!"))
                        .var(GraphQLVar.create("review").type("ReviewInput!"))
                        .object(GraphQLObject.create("createReview").arg("episode", GraphQLVar.create("ep")).arg("review", GraphQLVar.create("review"))
                                .field(GraphQLScalar.create("stars"))
                                .field(GraphQLScalar.create("commentary")))
                        .build());
    }
}
