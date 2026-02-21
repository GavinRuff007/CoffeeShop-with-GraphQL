package org.example.coffeestore.domain.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GraphQLExceptionHandler implements DataFetcherExceptionResolver {


    @Override
    public Mono<List<GraphQLError>> resolveException(Throwable ex, DataFetchingEnvironment env) {

            if (ex instanceof ResourceNotFoundException) {
                return (Mono<List<GraphQLError>>) GraphqlErrorBuilder.newError()
                        .message(ex.getMessage())
                        .errorType(ErrorType.NOT_FOUND)
                        .build();
            }

            return (Mono<List<GraphQLError>>) GraphqlErrorBuilder.newError()
                    .message("Internal error")
                    .errorType(ErrorType.INTERNAL_ERROR)
                    .build();

    }
}