package com.eunmin.v2;

import org.junit.Assert;
import org.junit.Test;

public class OperationDefinitionTest {
    enum Unit {
        FOOT
    }

    enum Episode {
        EMPIRE, JEDI
    }

    @Test
    public void testFields() {
        Field.builder().field().name("a").end();

        Assert.assertEquals(
                "query { hero { name appearsIn } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .field().name("hero")
                            .field().name("name")
                            .end()
                            .field().name("appearsIn")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testObjectField() {
        Assert.assertEquals(
                "query { hero { name friends { name } } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .field().name("hero")
                            .field().name("name")
                            .end()
                            .field().name("friends")
                                .field().name("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .field().name("human")
                            .arg("id", "1000")
                            .field().name("name")
                            .end()
                            .field().name("height")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testScalarArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height (unit: FOOT) } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .field().name("human")
                            .arg("id", "1000")
                            .field().name("name")
                            .end()
                            .field().name("height")
                                .arg("unit", Unit.FOOT)
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testAliases() {
        Assert.assertEquals(
                "query { empireHero: hero (episode: EMPIRE) { name } jediHero: hero (episode: JEDI) { name } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .field().alias("empireHero").name("hero")
                            .arg("episode", Episode.EMPIRE)
                            .field().name("name")
                            .end()
                        .end()
                        .field().alias("jediHero").name("hero")
                            .arg("episode", Episode.JEDI)
                            .field().name("name")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testFragmentSpreads() {
        Assert.assertEquals(
                "query { leftComparison: hero (episode: EMPIRE) { ... comparisonFields } rightComparison: hero (episode: JEDI) { ... comparisonFields } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .field().alias("leftComparison").name("hero")
                            .arg("episode", Episode.EMPIRE)
                            .fragmentSpread().name("comparisonFields")
                            .end()
                        .end()
                        .field().alias("rightComparison").name("hero")
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
                        .field().name("name")
                        .end()
                        .field().name("appearsIn")
                        .end()
                        .field().name("friends")
                            .field().name("name")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testOperationName() {
        Assert.assertEquals(
                "query HeroNameAndFriends { hero { name friends { name } } }",
                OperationDefinition.builder()
                        .name("HeroNameAndFriends")
                        .type(OperationType.Query)
                        .field().name("hero")
                            .field().name("name")
                            .end()
                            .field().name("friends")
                                .field().name("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testVariables() {
        Assert.assertEquals(
                "query HeroNameAndFriends ($episode: Episode) { hero (episode: $episode) { name friends { name } } }",
                OperationDefinition.builder()
                        .name("HeroNameAndFriends")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").build())
                        .type(OperationType.Query)
                        .field().name("hero")
                            .arg("episode", new VariableName("episode"))
                            .field().name("name")
                            .end()
                            .field().name("friends")
                                .field().name("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testVariablesDefaultValue() {
        Assert.assertEquals(
                "query HeroNameAndFriends ($episode: Episode = JEDI) { hero (episode: $episode) { name friends { name } } }",
                OperationDefinition.builder()
                        .name("HeroNameAndFriends")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").defaultValue(Episode.JEDI).build())
                        .type(OperationType.Query)
                        .field().name("hero")
                            .arg("episode", new VariableName("episode"))
                            .field().name("name")
                            .end()
                            .field().name("friends")
                                .field().name("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testIncludeDirectives() {
        Assert.assertEquals(
                "query Hero ($episode: Episode, $withFriends: Boolean!) { hero (episode: $episode) { name friends @include(if: $withFriends) { name } } }",
                OperationDefinition.builder()
                        .name("Hero")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").build())
                        .var(VariableDefinition.builder().name(new VariableName("withFriends")).type("Boolean!").build())
                        .type(OperationType.Query)
                        .field().name("hero")
                            .arg("episode", new VariableName("episode"))
                            .field().name("name")
                            .end()
                            .field().name("friends")
                                .include(new VariableName("withFriends"))
                                .field().name("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testSkipDirectives() {
        Assert.assertEquals(
                "query Hero ($episode: Episode, $withFriends: Boolean!) { hero (episode: $episode) { name friends @skip(if: $withFriends) { name } } }",
                OperationDefinition.builder()
                        .name("Hero")
                        .var(VariableDefinition.builder().name(new VariableName("episode")).type("Episode").build())
                        .var(VariableDefinition.builder().name(new VariableName("withFriends")).type("Boolean!").build())
                        .type(OperationType.Query)
                        .field().name("hero")
                            .arg("episode", new VariableName("episode"))
                            .field().name("name")
                            .end()
                            .field().name("friends")
                                .skip(new VariableName("withFriends"))
                                .field().name("name")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testMutation() {
        Assert.assertEquals(
                "mutation CreateReviewForEpisode ($ep: Episode!, $review: ReviewInput!) { createReview (episode: $ep, review: $review) { stars commentary } }",
                OperationDefinition.builder()
                        .name("CreateReviewForEpisode")
                        .var(VariableDefinition.builder().name(new VariableName("ep")).type("Episode!").build())
                        .var(VariableDefinition.builder().name(new VariableName("review")).type("ReviewInput!").build())
                        .type(OperationType.Mutation)
                        .field().name("createReview")
                            .arg("episode", new VariableName("ep"))
                            .arg("review", new VariableName("review"))
                            .field().name("stars")
                            .end()
                            .field().name("commentary")
                            .end()
                        .end()
                        .build().toString());
    }

    @Test
    public void testInlineFragment() {
        Assert.assertEquals(
                "query HeroForEpisode ($ep: Episode!) { hero (episode: $ep) { name ... on Droid { primaryFunction } ... on Human { height } } }",
                OperationDefinition.builder()
                        .name("HeroForEpisode")
                        .var(VariableDefinition.builder().name(new VariableName("ep")).type("Episode!").build())
                        .type(OperationType.Query)
                        .field().name("hero")
                            .arg("episode", new VariableName("ep"))
                            .field().name("name")
                            .end()
                            .inlineFragment().on("Droid")
                                .field().name("primaryFunction")
                                .end()
                            .end()
                            .inlineFragment().on("Human")
                                .field().name("height")
                                .end()
                            .end()
                        .end()
                        .build().toString());
    }
}
