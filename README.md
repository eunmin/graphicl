# Graphicl

## Example

### Fields

```graphql
query {
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

```java
class Hero {
  @GraphQLField
  String name;
  @GraphQLField
  String appearsIn;
}

@GraphQLQuery
class Query {
  @GraphQLField
  Hero hero;
}
```

```graphql
query {
  hero {
    name
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

```java
class Friend {
  @GraphQLField
  String name;
}

class Hero {
  @GraphQLField
  String name;
  @GraphQLField
  List<Firend> friends;
}

@GraphQLQuery
class Query {
  @GraphQLField
  Hero hero;
}
```

### Arguments

```graphql
query {
  human(id: "1000") {
    name
    height
  }
}
```

```java
GraphQL.query()
  .field("human").arg("id", "1000")
      .field("name")
      .end()
      .field("height")
      .end()
  .end()
  .build();
```

```java
class Human {
  @GraphQLField
  String name;
  @GraphQLField
  Integer height;
}

@GraphQLQuery
class Query {
  @GraphQLField(args = { @GraphQLArg(name = "id", value = "1000") })
  Human human;
}
```

```graphql
query {
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
  .field("human").arg("id", "1000")
      .field("name")
      .end()
      .field("height").arg("unit", Unit.FOOT)
      .end()
  .end()
  .build();
```

```java
class Human {
  @GraphQLField
  String name;
  @GraphQLField(args = { @GraphQLArg(name = "unit", value = Unit.FOOT) })
  Integer height;
}

@GraphQLQuery
class Query {
  @GraphQLField(args = { @GraphQLArg(name = "id", value = "1000") })
  Human human;
}
```

### Aliases

```graphql
query {
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
  .field("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
      .field("name")
      .end()
  .end()
  .field("hero").alias("jediHero").arg("episode", Episode.JEDI)
      .field("name")
      .end()
  .end()
  .build();            
```

```java
class Hero {
  @GraphQLField
  String name;
}

@GraphQLQuery
class Query {
  @GraphQLField(name = "hero", args = { @GraphQLArg(name = "episode", value = Episode.EMPIRE) })
  Hero empireHero;

  @GraphQLField(name = "hero", args = { @GraphQLArg(name = "episode", value = Episode.JEDI) })  
  Hero jediHero;
}
```

### Fragments

```graphql
query {
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
  .field("hero").alias("leftComparison").arg("episode", Episode.EMPIRE)
      .fragmentSpread("comparisonFields")
      .end()
  .end()
  .field("hero").alias("rightComparison").arg("episode", Episode.JEDI)
      .fragmentSpread("comparisonFields")
      .end()
  .end()
  .build();

GraphQL.fragmentDefinition("comparisonFields").on("Character")
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

#### Not support yet ...

```java
class Friend {
  @GraphQLField
  String name;
}

class Hero {
  @GraphQLField
  String name;
  @GraphQLField
  String appearsIn;
  @GraphQLField
  List<Friend> friends;
}

@GraphQLQuery
class Query {
  @GraphQLField(name = "hero", args = { @GraphQLArg(name = "episode", value = Episode.EMPIRE) })
  Hero leftComparison;

  @GraphQLField(name = "hero", args = { @GraphQLArg(name = "episode", value = Episode.JEDI) })  
  Hero rightComparison;
}
```

```graphql
query {
  leftComparison: hero(episode: EMPIRE) {
    name
    appearsIn
    friends {
      name
    }
  }
  rightComparison: hero(episode: JEDI) {
    name
    appearsIn
    friends {
      name
    }
  }
}
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

```java
class Friend {
  @GraphQLField
  String name;
}

class Hero {
  @GraphQLField
  String name;
  @GraphQLField
  List<Firend> friends;
}

@GraphQLQuery("HeroNameAndFriends")
class Query {
  @GraphQLField
  Hero hero;
}
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
  .var(GraphQL.var("episode")).type("Episode")
  .end()
  .field("hero").arg("episode", GraphQL.var("episode"))
      .field("name")
      .end()
      .field("friends")
          .field("name")
          .end()
      .end()
  .end()
  .build());                            
```

```java
class Friend {
  @GraphQLField
  String name;
}

class Hero {
  @GraphQLField
  String name;
  @GraphQLField
  List<Firend> friends;
}

@GraphQLQuery(name = "HeroNameAndFriends",
              vars = { @GraphQLVar(var = GraphQL.var("episode"), type = "Episode") })
class Query {
  @GraphQLField(args = { @GraphQLArg(name = "episode", value = GraphQL.var("episode")) })
  Hero hero;
}
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
  .var(GraphQL.var("episode")).type("Episode").defaultValue(Episode.JEDI)
  .end()
  .field("hero").arg("episode", GraphQL.var("episode"))
      .field("name")
      .end()
      .field("friends")
          .field("name")
          .end()
      .end()
  .end()
  .build());                            
```

```java
class Friend {
  @GraphQLField
  String name;
}

class Hero {
  @GraphQLField
  String name;
  @GraphQLField
  List<Firend> friends;
}

@GraphQLQuery(name = "HeroNameAndFriends",
              vars = { @GraphQLVar(var = GraphQL.var("episode"), type = "Episode", defaultValue = Episode.JEDI) })
class Query {
  @GraphQLField(args = { @GraphQLArg(name = "episode", value = GraphQL.var("episode")) })
  Hero hero;
}
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
  .var(GraphQL.var("episode")).type("Episode")
  .end()
  .var(GraphQL.var("withFriends")).type("Boolean!")
  .end()
  .field("hero").arg("episode", GraphQL.var("episode"))
      .field("name")
      .end()
      .field("friends").include(GraphQL.var("withFriends"))
          .field("name")
          .end()
      .end()
  .end()
  .build());                           
```

```java
class Friend {
  @GraphQLField
  String name;
}

class Hero {
  @GraphQLField
  String name;
  @GraphQLField(include = GraphQL.var("withFriends"))
  List<Friend> friends;
}

@GraphQLQuery(name = "Hero",
              vars = {@GraphQLVar(var = GraphQL.var("episode"), type = "Episode"),
                      @GraphQLVar(var = GraphQL.var("withFriends"), type = "Boolean!")})
class Query {
  @GraphQLField(args = { @GraphQLArg(name = "episode", value = GraphQL.var("episode")) })
  Hero hero;
}
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
  .var(GraphQL.var("ep")).type("Episode!")
  .end()
  .var(GraphQL.var("review")).type("ReviewInput!")
  .end()
  .field("createReview").arg("episode", GraphQL.var("ep")).arg("review", GraphQL.var("review"))
      .field("stars")
      .end()
      .field("commentary")
      .end()
  .end()
  .build());      
```

```java
class CreateReview {
  @GraphQLField
  Integer stars;
  @GraphQLField
  String commentary;
}

@GraphQLMutation(name = "CreateReviewForEpisode",
                 vars = {@GraphQLVar(var = GraphQL.var("ep"), type = "Episode!"),
                         @GraphQLVar(var = GraphQL.var("review"), type = "ReviewInput!")})
class Mutation {
  @GraphQLField(args = { @GraphQLArg(name = "episode", value = GraphQL.var("ep")),
                         @GraphQLArg(name = "review", value = GraphQL.var("review"))})
  CreateReview createReview;
}
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
    .var(GraphQL.var("ep")).type("Episode!")
    .end()
    .field().name("hero").arg("episode", GraphQL.var("ep"))
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

```java
class Hero {
  @GraphQLField
  String name;
  @GraphQLInlineFragment(on = "Droid")
  String primaryFunction;
  @GraphQLInlineFragment(on = "Human")
  Integer height;
}

@GraphQLQuery(name = "HeroForEpisode",
              vars = {@GraphQLVar(var = GraphQL.var("ep"), type = "Episode!")})
class Query {
  @GraphQLField(@GraphQLArg(name = "episode", value = GraphQL.var("ep")))
  Hero hero;
}
```
