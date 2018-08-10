# Graphicl

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
    Object.create("hero")
            .field(Scalar.create("name"))
            .field(Scalar.create("appearsIn")));
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
    Object.create("hero")
            .field(Scalar.create("name"))
            .field(Object.create("friends")
                            .filed(Scalar.create("name"))));
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
    Object.create("human").arg("id", "1000")
            .field(Scalar.create("name"))
            .field(Scalar.create("height")));
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
    Object.create("human").arg("id", "1000")
            .field(Scalar.create("name"))
            .field(Scalar.create("height").arg("unit", Unit.FOOT)));
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
    Object.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
            .field(Scalar.create("name")))
  .object(
    Object.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
            .field(Scalar.create("name")));
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
    Object.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
            .fragment("comparisonFields")
  .object(
    Object.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
            .fragment("comparisonFields")
  .fragment(
    Fragment.create("comparisonFields").on("Character")
              .field(Scalar.create("name")))
              .field(Scalar.create("appearsIn"))))
              .field(Object.create("friends")
                              .filed(Scalar.create("name"))));
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
    Object.create("hero")
            .field(Scalar.create("name"))
            .field(Object.create("friends")
                            .filed(Scalar.create("name"))));
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
    Object.create("hero").arg("episode", "$episode")
            .field(Scalar.create("name"))
            .field(Object.create("friends")
                            .filed(Scalar.create("name"))));

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
    Object.create("hero").arg("episode", "$episode")
            .field(Scalar.create("name"))
            .field(Object.create("friends")
                            .filed(Scalar.create("name"))));
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
    Object.create("hero").arg("episode", "$episode")
            .field(Scalar.create("name"))
            .field(Object.create("friends")
                            .include("$withFriends") // .skip("$withFriends")
                            .filed(Scalar.create("name"))));
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
    Object.create("createReview").arg("episode", "$episode").arg("review", "$review")
      .filed(Scalar.create("stars"))
      .filed(Scalar.create("commentary")));
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
    Object.create("hero").arg("episode", "$ep")
      .field(Scalar.create("name"))
      .fragment(
        Fragment.create().on("Droid")
          .field(Scalar.create("primaryFunction"))))
      .fragment(
        Fragment.create().on("Human")
          .field(Scalar.create("height")))));
```
