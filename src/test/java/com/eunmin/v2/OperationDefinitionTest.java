package com.eunmin.v2;

import com.eunmin.*;
import com.eunmin.v2.builder.*;
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
                OperationDefinitionBuilder.create()
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("appearsIn").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testObjectField() {
        Assert.assertEquals(
                "query { hero { name friends { name } } }",
                OperationDefinitionBuilder.create()
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("friends")
                                        .select(FieldBuilder.create("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height } }",
                OperationDefinitionBuilder.create()
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("human")
                                .arg("id", "1000")
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("height").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testScalarArguments() {
        Assert.assertEquals(
                "query { human (id: \"1000\") { name height (unit: FOOT) } }",
                OperationDefinitionBuilder.create()
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("human")
                                .arg("id", "1000")
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("height")
                                        .arg("unit", Unit.FOOT)
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testAliases() {
        Assert.assertEquals(
                "query { empireHero: hero (episode: EMPIRE) { name } jediHero: hero (episode: JEDI) { name } }",
                OperationDefinitionBuilder.create()
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .alias("empireHero")
                                .arg("episode", Episode.EMPIRE)
                                .select(FieldBuilder.create("name").build())
                                .build())
                        .select(FieldBuilder.create("hero")
                                .alias("jediHero")
                                .arg("episode", Episode.JEDI)
                                .select(FieldBuilder.create("name").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testFragmentSpreads() {
        Assert.assertEquals(
                "query { leftComparison: hero (episode: EMPIRE) { ... comparisonFields } rightComparison: hero (episode: JEDI) { ... comparisonFields } }",
                OperationDefinitionBuilder.create()
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .alias("leftComparison")
                                .arg("episode", Episode.EMPIRE)
                                .select(FragmentSpreadBuilder.create("comparisonFields").build())
                                .build())
                        .select(FieldBuilder.create("hero")
                                .alias("rightComparison")
                                .arg("episode", Episode.JEDI)
                                .select(FragmentSpreadBuilder.create("comparisonFields").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testFragment() {
        Assert.assertEquals(
                "fragment comparisonFields on Character { name appearsIn friends { name } }",
                FragmentDefinitionBuilder.create("comparisonFields")
                        .on("Character")
                        .select(FieldBuilder.create("name").build())
                        .select(FieldBuilder.create("appearsIn").build())
                        .select(FieldBuilder.create("friends")
                                .select(FieldBuilder.create("name").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testOperationName() {
        Assert.assertEquals(
                "query HeroNameAndFriends { hero { name friends { name } } }",
                OperationDefinitionBuilder.create("HeroNameAndFriends")
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("friends")
                                        .select(FieldBuilder.create("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testVariables() {
        Assert.assertEquals(
                "query HeroNameAndFriends ($episode: Episode) { hero (episode: $episode) { name friends { name } } }",
                OperationDefinitionBuilder.create("HeroNameAndFriends")
                        .var(VariableDefinitionBuilder.create(new VariableName("episode"), "Episode").build())
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("friends")
                                        .select(FieldBuilder.create("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testVariablesDefaultValue() {
        Assert.assertEquals(
                "query HeroNameAndFriends ($episode: Episode = JEDI) { hero (episode: $episode) { name friends { name } } }",
                OperationDefinitionBuilder.create("HeroNameAndFriends")
                        .var(VariableDefinitionBuilder.create(new VariableName("episode"), "Episode").defaultValue(Episode.JEDI).build())
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("friends")
                                        .select(FieldBuilder.create("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testIncludeDirectives() {
        Assert.assertEquals(
                "query Hero ($episode: Episode, $withFriends: Boolean!) { hero (episode: $episode) { name friends @include(if: $withFriends) { name } } }",
                OperationDefinitionBuilder.create("Hero")
                        .var(VariableDefinitionBuilder.create(new VariableName("episode"), "Episode").build())
                        .var(VariableDefinitionBuilder.create(new VariableName("withFriends"), "Boolean!").build())
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("friends")
                                        .include(new VariableName("withFriends"))
                                        .select(FieldBuilder.create("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testSkipDirectives() {
        Assert.assertEquals(
                "query Hero ($episode: Episode, $withFriends: Boolean!) { hero (episode: $episode) { name friends @skip(if: $withFriends) { name } } }",
                OperationDefinitionBuilder.create("Hero")
                        .var(VariableDefinitionBuilder.create(new VariableName("episode"), "Episode").build())
                        .var(VariableDefinitionBuilder.create(new VariableName("withFriends"), "Boolean!").build())
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .arg("episode", new VariableName("episode"))
                                .select(FieldBuilder.create("name").build())
                                .select(FieldBuilder.create("friends")
                                        .skip(new VariableName("withFriends"))
                                        .select(FieldBuilder.create("name").build())
                                        .build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testMutation() {
        Assert.assertEquals(
                "mutation CreateReviewForEpisode ($ep: Episode!, $review: ReviewInput!) { createReview (episode: $ep, review: $review) { stars commentary } }",
                OperationDefinitionBuilder.create("CreateReviewForEpisode")
                        .var(VariableDefinitionBuilder.create(new VariableName("ep"), "Episode!").build())
                        .var(VariableDefinitionBuilder.create(new VariableName("review"), "ReviewInput!").build())
                        .type(OperationType.Mutation)
                        .select(FieldBuilder.create("createReview")
                                .arg("episode", new VariableName("ep"))
                                .arg("review", new VariableName("review"))
                                .select(FieldBuilder.create("stars").build())
                                .select(FieldBuilder.create("commentary").build())
                                .build())
                        .build().toString());
    }

    @Test
    public void testInlineFragment() {
        Assert.assertEquals(
                "query HeroForEpisode ($ep: Episode!) { hero (episode: $ep) { name ... on Droid { primaryFunction } ... on Human { height } } }",
                OperationDefinitionBuilder.create("HeroForEpisode")
                        .var(VariableDefinitionBuilder.create(new VariableName("ep"), "Episode!").build())
                        .type(OperationType.Query)
                        .select(FieldBuilder.create("hero")
                                .arg("episode", new VariableName("ep"))
                                .select(FieldBuilder.create("name").build())
                                .select(InlineFragmentBuilder.create().on("Droid")
                                        .select(FieldBuilder.create("primaryFunction").build())
                                        .build())
                                .select(InlineFragmentBuilder.create().on("Human")
                                        .select(FieldBuilder.create("height").build())
                                        .build())
                                .build())
                        .build().toString());
    }
}
