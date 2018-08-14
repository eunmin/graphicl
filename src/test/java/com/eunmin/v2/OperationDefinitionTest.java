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
        Assert.assertEquals(
                "query { hero { name appearsIn } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .select(Field.builder()
                                .name("hero")
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("appearsIn").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testObjectField() {
        Assert.assertEquals(
                "query { hero { name friends { name } } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .select(Field.builder().name("hero")
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("friends")
                                        .select(Field.builder().name("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .select(Field.builder().name("human")
                                .arg("id", "1000")
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("height").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testScalarArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height (unit: FOOT) } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .select(Field.builder().name("human")
                                .arg("id", "1000")
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("height")
                                        .arg("unit", Unit.FOOT)
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testAliases() {
        Assert.assertEquals(
                "query { empireHero: hero (episode: EMPIRE) { name } jediHero: hero (episode: JEDI) { name } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .select(Field.builder().name("hero")
                                .alias("empireHero")
                                .arg("episode", Episode.EMPIRE)
                                .select(Field.builder().name("name").build())
                                .build())
                        .select(Field.builder().name("hero")
                                .alias("jediHero")
                                .arg("episode", Episode.JEDI)
                                .select(Field.builder().name("name").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testFragmentSpreads() {
        Assert.assertEquals(
                "query { leftComparison: hero (episode: EMPIRE) { ... comparisonFields } rightComparison: hero (episode: JEDI) { ... comparisonFields } }",
                OperationDefinition.builder()
                        .type(OperationType.Query)
                        .select(Field.builder().name("hero")
                                .alias("leftComparison")
                                .arg("episode", Episode.EMPIRE)
                                .select(FragmentSpread.builder().name("comparisonFields").build())
                                .build())
                        .select(Field.builder().name("hero")
                                .alias("rightComparison")
                                .arg("episode", Episode.JEDI)
                                .select(FragmentSpread.builder().name("comparisonFields").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testFragment() {
        Assert.assertEquals(
                "fragment comparisonFields on Character { name appearsIn friends { name } }",
                FragmentDefinition.builder()
                        .name("comparisonFields")
                        .on("Character")
                        .select(Field.builder().name("name").build())
                        .select(Field.builder().name("appearsIn").build())
                        .select(Field.builder().name("friends")
                                .select(Field.builder().name("name").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testOperationName() {
        Assert.assertEquals(
                "query HeroNameAndFriends { hero { name friends { name } } }",
                OperationDefinition.builder()
                        .name("HeroNameAndFriends")
                        .type(OperationType.Query)
                        .select(Field.builder().name("hero")
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("friends")
                                        .select(Field.builder().name("name").build())
                                        .build())
                                .build())
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
                        .select(Field.builder().name("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("friends")
                                        .select(Field.builder().name("name").build())
                                        .build())
                                .build())
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
                        .select(Field.builder().name("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("friends")
                                        .select(Field.builder().name("name").build())
                                        .build())
                                .build())
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
                        .select(Field.builder().name("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("friends")
                                        .include(new VariableName("withFriends"))
                                        .select(Field.builder().name("name").build())
                                        .build())
                                .build())
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
                        .select(Field.builder().name("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(Field.builder().name("name").build())
                                .select(Field.builder().name("friends")
                                        .skip(new VariableName("withFriends"))
                                        .select(Field.builder().name("name").build())
                                        .build())
                                .build())
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
                        .select(Field.builder().name("createReview")
                                .arg("episode", new VariableName("ep"))
                                .arg("review", new VariableName("review"))
                                .select(Field.builder().name("stars").build())
                                .select(Field.builder().name("commentary").build())
                                .build())
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
                        .select(Field.builder().name("hero")
                                .arg("episode", new VariableName("ep"))
                                .select(Field.builder().name("name").build())
                                .select(InlineFragment.builder().on("Droid")
                                        .select(Field.builder().name("primaryFunction").build())
                                        .build())
                                .select(InlineFragment.builder().on("Human")
                                        .select(Field.builder().name("height").build())
                                        .build())
                                .build())
                        .build().toString());
    }
}
