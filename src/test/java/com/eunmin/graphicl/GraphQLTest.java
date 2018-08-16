package com.eunmin.graphicl;

import org.junit.Assert;
import org.junit.Test;

public class GraphQLTest {
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
                GraphQL.query()
                        .field("hero")
                            .field("name")
                            .end()
                            .field("appearsIn")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testObjectField() {
        Assert.assertEquals(
                "query { hero { name friends { name } } }",
                GraphQL.query()
                        .field("hero")
                            .field("name")
                            .end()
                            .field("friends")
                                .field("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height } }",
                GraphQL.query()
                        .field("human")
                            .arg("id", "1000")
                            .field("name")
                            .end()
                            .field("height")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testScalarArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height (unit: FOOT) } }",
                GraphQL.query()
                        .field("human")
                            .arg("id", "1000")
                            .field("name")
                            .end()
                            .field("height")
                                .arg("unit", Unit.FOOT)
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testAliases() {
        Assert.assertEquals(
                "query { empireHero: hero (episode: EMPIRE) { name } jediHero: hero (episode: JEDI) { name } }",
                GraphQL.query()
                        .field("hero").alias("empireHero")
                            .arg("episode", Episode.EMPIRE)
                            .field("name")
                            .end()
                        .end()
                        .field("hero").alias("jediHero")
                            .arg("episode", Episode.JEDI)
                            .field("name")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testFragmentSpreads() {
        Assert.assertEquals(
                "query { leftComparison: hero (episode: EMPIRE) { ... comparisonFields } rightComparison: hero (episode: JEDI) { ... comparisonFields } }",
                GraphQL.query()
                        .field("hero").alias("leftComparison")
                            .arg("episode", Episode.EMPIRE)
                            .fragmentSpread().name("comparisonFields")
                            .end()
                        .end()
                        .field("hero").alias("rightComparison")
                            .arg("episode", Episode.JEDI)
                            .fragmentSpread().name("comparisonFields")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testFragment() {
        Assert.assertEquals(
                "fragment comparisonFields on Character { name appearsIn friends { name } }",
                FragmentDefinition.builder()
                        .name("comparisonFields")
                        .on("Character")
                        .field("name")
                        .end()
                        .field("appearsIn")
                        .end()
                        .field("friends")
                            .field("name")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testOperationName() {
        Assert.assertEquals(
                "query HeroNameAndFriends { hero { name friends { name } } }",
                GraphQL.query("HeroNameAndFriends")
                        .field("hero")
                            .field("name")
                            .end()
                            .field("friends")
                                .field("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testVariables() {
        Assert.assertEquals(
                "query HeroNameAndFriends ($episode: Episode) { hero (episode: $episode) { name friends { name } } }",
                GraphQL.query("HeroNameAndFriends")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").build())
                        .field("hero")
                            .arg("episode", new VariableName("episode"))
                            .field("name")
                            .end()
                            .field("friends")
                                .field("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testVariablesDefaultValue() {
        Assert.assertEquals(
                "query HeroNameAndFriends ($episode: Episode = JEDI) { hero (episode: $episode) { name friends { name } } }",
                GraphQL.query("HeroNameAndFriends")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").defaultValue(Episode.JEDI).build())
                        .field("hero")
                            .arg("episode", new VariableName("episode"))
                            .field("name")
                            .end()
                            .field("friends")
                                .field("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testIncludeDirectives() {
        Assert.assertEquals(
                "query Hero ($episode: Episode, $withFriends: Boolean!) { hero (episode: $episode) { name friends @include(if: $withFriends) { name } } }",
                GraphQL.query("Hero")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").build())
                        .var(VariableDefinition.builder().name(new VariableName("withFriends")).type("Boolean!").build())
                        .field("hero")
                            .arg("episode", new VariableName("episode"))
                            .field("name")
                            .end()
                            .field("friends")
                                .include(new VariableName("withFriends"))
                                .field("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testSkipDirectives() {
        Assert.assertEquals(
                "query Hero ($episode: Episode, $withFriends: Boolean!) { hero (episode: $episode) { name friends @skip(if: $withFriends) { name } } }",
                GraphQL.query("Hero")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").build())
                        .var(VariableDefinition.builder().name(new VariableName("withFriends")).type("Boolean!").build())
                        .field("hero")
                            .arg("episode", new VariableName("episode"))
                            .field("name")
                            .end()
                            .field("friends")
                                .skip(new VariableName("withFriends"))
                                .field("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testMutation() {
        Assert.assertEquals(
                "mutation CreateReviewForEpisode ($ep: Episode!, $review: ReviewInput!) { createReview (episode: $ep, review: $review) { stars commentary } }",
                GraphQL.mutation("CreateReviewForEpisode")
                        .var(VariableDefinition.builder().name(new VariableName("ep")).type("Episode!").build())
                        .var(VariableDefinition.builder().name(new VariableName("review")).type("ReviewInput!").build())
                        .field("createReview")
                            .arg("episode", new VariableName("ep"))
                            .arg("review", new VariableName("review"))
                            .field("stars")
                            .end()
                            .field("commentary")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testInlineFragment() {
        Assert.assertEquals(
                "query HeroForEpisode ($ep: Episode!) { hero (episode: $ep) { name ... on Droid { primaryFunction } ... on Human { height } } }",
                GraphQL.query("HeroForEpisode")
                        .var(VariableDefinition.builder().name(new VariableName("ep")).type("Episode!").build())
                        .field().name("hero")
                            .arg("episode", new VariableName("ep"))
                            .field("name")
                            .end()
                            .inlineFragment().on("Droid")
                                .field("primaryFunction")
                                .end()
                            .end()
                            .inlineFragment().on("Human")
                                .field("height")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }
}
