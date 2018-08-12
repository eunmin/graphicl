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
GraphQLQuery.create()
  .object(
    GraphQLObject.create("hero")
      .field(GraphQLScalar.create("name"))
      .field(GraphQLScalar.create("appearsIn")))
  .build();
```

```java
@GraphQLQuery("hero")
class Query {
  @GraphQLField
  String name;

  @GraphQLField
  List<Episode> appearsIn;
}
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
GraphQLQuery.create()
  .object(
    GraphQLObject.create("hero")
      .field(GraphQLScalar.create("name"))
      .field(GraphQLObject.create("friends")
        .filed(GraphQLScalar.create("name"))))
  .build();
```

```java
@GraphQLQuery("hero")
class Query {
  @GraphQLField
  String name;

  @GraphQLField
  List<Friend> friends;
}

@GraphqQLObject
class Friend {
  @GraphQLField
  String name;
}
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
GraphQLQuery.create()
  .object(
    GraphQLObject.create("human").arg("id", "1000")
      .field(GraphQLScalar.create("name"))
      .field(GraphQLScalar.create("height")))
  .build();
```

```java
@GraphQLQuery(name = "human", args = {"id", "1000"})
class Query {
  @GraphQLField
  String name;

  @GraphQLField
  Int height;
}
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

GraphQLQuery.create()
  .object(
    GraphQLObject.create("human").arg("id", "1000")
      .field(GraphQLScalar.create("name"))
      .field(GraphQLScalar.create("height").arg("unit", Unit.FOOT)))
  .build();
```

```java
@GraphQLQuery(name = "human", args = {"id", "1000"})
class Query {
  @GraphQLField
  String name;

  @GraphQLField(args = {"unit", FOOT})
  Int height;
}
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

GraphQLQuery.create()
  .object(
    GraphQLObject.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
      .field(GraphQLScalar.create("name")))
  .object(
    GraphQLObject.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
      .field(GraphQLScalar.create("name")))
  .build();            
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

GraphQLQuery.create()
  .object(
    GraphQLObject.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
      .field(GraphQLFragmentSpread.create("comparisonFields"))
  .object(
    GraphQLObject.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
      .field(GraphQLFragmentSpread.craete("comparisonFields"))
  .build();

GraphQLFragment.create("comparisonFields").on("Character")
  .field(GraphQLScalar.create("name")))
  .field(GraphQLScalar.create("appearsIn"))))
  .field(GraphQLObject.create("friends")
    .field(GraphQLScalar.create("name"))))
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
GraphQLQuery.create("HeroNameAndFriends")
  .object(GraphQLObject.create("hero")
    .field(GraphQLScalar.create("name"))
    .field(GraphQLObject.create("friends")
      .field(GraphQLScalar.create("name"))))
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
GraphQLQuery.create("HeroNameAndFriends")
  .var(GraphQLVar.create("episode").type("Episode"))
  .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
    .field(GraphQLScalar.create("name"))
    .field(GraphQLObject.create("friends")
      .field(GraphQLScalar.create("name"))))
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
GraphQLQuery.create("HeroNameAndFriends")
  .var(GraphQLVar.create("episode").type("Episode").defaultValue(Episode.JEDI))
  .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
    .field(GraphQLScalar.create("name"))
    .field(GraphQLObject.create("friends")
      .field(GraphQLScalar.create("name"))))
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
GraphQLQuery.create("Hero")
  .var(GraphQLVar.create("episode").type("Episode"))
  .var(GraphQLVar.create("withFriends").type("Boolean!"))
  .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("episode"))
    .field(GraphQLScalar.create("name"))
    .field(GraphQLObject.create("friends")
      .include(GraphQLVar.create("withFriends"))
      .field(GraphQLScalar.create("name"))))
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
GraphQLQuery.create("CreateReviewForEpisode").mutation()
  .var(GraphQLVar.create("ep").type("Episode!"))
  .var(GraphQLVar.create("review").type("ReviewInput!"))
  .object(GraphQLObject.create("createReview").arg("episode", GraphQLVar.create("ep")).arg("review", GraphQLVar.create("review"))
    .field(GraphQLScalar.create("stars"))
    .field(GraphQLScalar.create("commentary")))
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
GraphQLQuery.create("HeroForEpisode")
    .var(GraphQLVar.create("ep").type("Episode!"))
    .object(GraphQLObject.create("hero").arg("episode", GraphQLVar.create("ep"))
      .field(GraphQLScalar.create("name"))
      .field(GraphQLInlineFragment.create().on("Droid")
        .field(GraphQLScalar.create("primaryFunction")))
      .field(GraphQLInlineFragment.create().on("Human")
        .field(GraphQLScalar.create("height"))))
    .build());
```
