import com.eunmin.GraphQLObject;
import com.eunmin.GraphQLQuery;
import com.eunmin.GraphQLScalar;

public class Main {
    enum Unit {
        FOOT
    }

    enum Episode {
        EMPIRE, JEDI
    }

    public static void main(String[] args) {
        System.out.println(
            GraphQLQuery.create()
                .object(GraphQLObject.create("hero")
                        .field(GraphQLScalar.create("name"))
                        .field(GraphQLScalar.create("appearsIn")))
                .build());

        System.out.println(
                GraphQLQuery.create()
                        .object(GraphQLObject.create("hero")
                                    .field(GraphQLScalar.create("name"))
                                    .field(GraphQLObject.create("friends")
                                            .field(GraphQLScalar.create("name"))))
                        .build());

        System.out.println(
                GraphQLQuery.create()
                        .object(GraphQLObject.create("human").arg("id", "1000")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLScalar.create("height")))
                        .build());

        System.out.println(
                GraphQLQuery.create()
                        .object(GraphQLObject.create("human").arg("id", "1000")
                                .field(GraphQLScalar.create("name"))
                                .field(GraphQLScalar.create("height").arg("unit", Unit.FOOT)))
                        .build());

        System.out.println(
                GraphQLQuery.create()
                        .object(GraphQLObject.create("hero").alias("empireHero").arg("episode", Episode.EMPIRE)
                                .field(GraphQLScalar.create("name")))
                        .object(GraphQLObject.create("hero").alias("jediHero").arg("episode", Episode.JEDI)
                                .field(GraphQLScalar.create("name")))
                        .build());
    }
}
