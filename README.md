# Graphicl

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
GraphQL.query()
  .field("hero")
      .field("name")
      .end()
      .field("appearsIn")
      .end()
  .end()
  .build();
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
GraphQL.query()
  .field("hero")
      .field("name")
      .end()
      .field("friends")
          .field("name")
          .end()
      .end()
  .end()
  .build();
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
GraphQL.query()
  .field("human")
      .arg("id", "1000")
      .field("name")
      .end()
      .field("height")
      .end()
  .end()
  .build();
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

GraphQL.query()
  .field("human")
      .arg("id", "1000")
      .field("name")
      .end()
      .field("height")
          .arg("unit", Unit.FOOT)
      .end()
  .end()
  .build();
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
  .build();            
```

```java

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
  .build();

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
  .build();
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
GraphQL.query("HeroNameAndFriends")
  .field("hero")
      .field("name")
      .end()
      .field("friends")
          .field("name")
          .end()
      .end()
  .end()
  .build());                          
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
GraphQL.query("HeroNameAndFriends")
  .var(VariableDefinition.builder().name(GraphQL.var("episode")).type("Episode").build())
  .field("hero")
      .arg("episode", GraphQL.var("episode"))
      .field("name")
      .end()
      .field("friends")
          .field("name")
          .end()
      .end()
  .end()
  .build());                            

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
GraphQL.query("HeroNameAndFriends")
  .var(VariableDefinition.builder().name(GraphQL.var("episode")).type("Episode").defaultValue(Episode.JEDI).build())
  .field("hero")
      .arg("episode", GraphQL.var("episode"))
      .field("name")
      .end()
      .field("friends")
          .field("name")
          .end()
      .end()
  .end()
  .build());                            
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
GraphQL.query("Hero")
  .var(VariableDefinition.builder().name(GraphQL.var("episode")).type("Episode").build())
  .var(VariableDefinition.builder().name(GraphQL.var("withFriends")).type("Boolean!").build())
  .field("hero")
      .arg("episode", GraphQL.var("episode"))
      .field("name")
      .end()
      .field("friends")
          .include(GraphQL.var("withFriends"))
          .field("name")
          .end()
      .end()
  .end()
  .build());                           
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
GraphQL.mutation("CreateReviewForEpisode")
  .var(VariableDefinition.builder().name(GraphQL.var("ep")).type("Episode!").build())
  .var(VariableDefinition.builder().name(GraphQL.var("review")).type("ReviewInput!").build())
  .field("createReview")
      .arg("episode", GraphQL.var("ep"))
      .arg("review", GraphQL.var("review"))
      .field("stars")
      .end()
      .field("commentary")
      .end()
  .end()
  .build());      
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
GraphQL.query("HeroForEpisode")
    .var(VariableDefinition.builder().name(GraphQL.var("ep")).type("Episode!").build())
    .field().name("hero")
        .arg("episode", GraphQL.var("ep"))
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
    .build());
```
