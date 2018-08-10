QL# Graphicl

GraphQL query builder for java.

## Example

### Fields

```graphql
{
  hero {
    name
    appearsIn
  }
}
```

```java
Query.create()
  .object(
    GraphQLObject.create("hero")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLScalar.create("appearsIn")));
```

```graphql
{
  hero {
    name
    # Queries can have comments!
    friends {
      name
    }
  }
}
```

```java
Query.create()
  .object(
    GraphQLObject.create("hero")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLObject.create("friends")
                            .filed(GraphQLScalar.create("name"))));
```

### Arguments

```graphql
{
  human(id: "1000") {
    name
    height
  }
}
```

```java
Query.create()
  .object(
    GraphQLObject.create("human").arg("id", "1000")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLScalar.create("height")));
```

```graphql
{
  human(id: "1000") {
    name
    height(unit: FOOT)
  }
}
```

```java
enum Unit {
    FOOT
}

Query.create()
  .object(
    GraphQLObject.create("human").arg("id", "1000")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLScalar.create("height").arg("unit", Unit.FOOT)));
```

### Aliases

```graphql
{
  empireHero: hero(episode: EMPIRE) {
    name
  }
  jediHero: hero(episode: JEDI) {
    name
  }
}
```

```java
enum Episode {
    EMPIRE, JEDI
}

Query.create()
  .object(
    GraphQLObject.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
            .field(GraphQLScalar.create("name")))
  .object(
    GraphQLObject.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
            .field(GraphQLScalar.create("name")));
```

### Fragments

```graphql
{
  leftComparison: hero(episode: EMPIRE) {
    ...comparisonFields
  }
  rightComparison: hero(episode: JEDI) {
    ...comparisonFields
  }
}

fragment comparisonFields on Character {
  name
  appearsIn
  friends {
    name
  }
}
```

```java
enum Episode {
    EMPIRE, JEDI
}

Query.create()
  .object(
    GraphQLObject.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
            .fragment("comparisonFields")
  .object(
    GraphQLObject.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
            .fragment("comparisonFields")
  .fragment(
    GraphQLFragment.create("comparisonFields").on("Character")
              .field(GraphQLScalar.create("name")))
              .field(GraphQLScalar.create("appearsIn"))))
              .field(GraphQLObject.create("friends")
                              .filed(GraphQLScalar.create("name"))));
```

### Operation name

```graphql
query HeroNameAndFriends {
  hero {
    name
    friends {
      name
    }
  }
}
```

```java
Query.create("HeroNameAndFriends")
  .object(
    GraphQLObject.create("hero")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLObject.create("friends")
                            .filed(GraphQLScalar.create("name"))));
```

### Variables

```graphql
query HeroNameAndFriends($episode: Episode) {
  hero(episode: $episode) {
    name
    friends {
      name
    }
  }
}

{
  "episode": "JEDI"
}
```

```java
Query.create("HeroNameAndFriends")
  .var("$episode", "Episode")
  .object(
    GraphQLObject.create("hero").arg("episode", "$episode")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLObject.create("friends")
                            .filed(GraphQLScalar.create("name"))));

Map variables = new HashMap<String, Object>();
variables.put("episode", "JEDI")
```

#### Default variables

```graphql
query HeroNameAndFriends($episode: Episode = JEDI) {
  hero(episode: $episode) {
    name
    friends {
      name
    }
  }
}
```

```java
Query.create("HeroNameAndFriends")
  .var("$episode", "Episode", Episode.JEDI)
  .object(
    GraphQLObject.create("hero").arg("episode", "$episode")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLObject.create("friends")
                            .filed(GraphQLScalar.create("name"))));
```

### Directives

```graphql
query Hero($episode: Episode, $withFriends: Boolean!) {
  hero(episode: $episode) {
    name
    friends @include(if: $withFriends) {
      name
    }
  }
}
```

```java
Query.create("Hero")
  .var("$episode", "Episode")
  .var("$withFriends", "Boolean!")
  .object(
    GraphQLObject.create("hero").arg("episode", "$episode")
            .field(GraphQLScalar.create("name"))
            .field(GraphQLObject.create("friends")
                            .include("$withFriends") // .skip("$withFriends")
                            .filed(GraphQLScalar.create("name"))));
```

### Mutations

```graphql
mutation CreateReviewForEpisode($ep: Episode!, $review: ReviewInput!) {
  createReview(episode: $ep, review: $review) {
    stars
    commentary
  }
}
```

```java
Mutation.create("CreateReviewForEpisode")
  .var("$ep", "Episode!")
  .var("$review", "ReviewInput!")
  .object(
    GraphQLObject.create("createReview").arg("episode", "$episode").arg("review", "$review")
      .filed(GraphQLScalar.create("stars"))
      .filed(GraphQLScalar.create("commentary")));
```

### Inline Fragments

```graphql
query HeroForEpisode($ep: Episode!) {
  hero(episode: $ep) {
    name
    ... on Droid {
      primaryFunction
    }
    ... on Human {
      height
    }
  }
}
```

```java
Query.create("HeroForEpisode")
  .var("$ep", "Episode!")
  .object(
    GraphQLObject.create("hero").arg("episode", "$ep")
      .field(GraphQLScalar.create("name"))
      .fragment(
        GraphQLFragment.create().on("Droid")
          .field(GraphQLScalar.create("primaryFunction"))))
      .fragment(
        GraphQLFragment.create().on("Human")
          .field(GraphQLScalar.create("height")))));
```
