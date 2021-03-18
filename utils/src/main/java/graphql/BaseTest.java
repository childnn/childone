package graphql;

import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 15:33
 */
public class BaseTest {

    @Test
    void graphql() {
        String schema = "type Query{hello: String} schema{query: Query}";

        final SchemaParser schemaParser = new SchemaParser();
        final TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        final RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring().type("Query",
                builder -> builder.dataFetcher("hello",
                        new StaticDataFetcher("world")))
                .build();

        final SchemaGenerator schemaGenerator = new SchemaGenerator();
        final GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        final GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        final ExecutionResult executionResult = graphQL.execute("{hello}");

        System.out.println(executionResult.getData().toString());
    }

}
