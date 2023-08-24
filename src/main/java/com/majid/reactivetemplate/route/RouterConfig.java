package com.majid.reactivetemplate.route;

import com.majid.reactivetemplate.exception.InvalidParameterException;
import com.majid.reactivetemplate.handler.BookHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class RouterConfig {

    @Bean
    @ApiInfo
    public RouterFunction<ServerResponse> eventRoutes(BookHandler bookHandler) {
        return route()
                .path("/book", b1 -> b1
                        .nest(accept(APPLICATION_JSON), b2 -> b2
                                .POST(contentType(APPLICATION_JSON), bookHandler::saveBook)
                                .GET("/{id:[0-9]+}", bookHandler::findBook)
                                .GET(bookHandler::findBooks)))
                .onError(InvalidParameterException.class, (e, req) -> {
                    log.error(e.getMessage(), e);
                    return ServerResponse.badRequest()
                            .bodyValue(e.getErrors().stream()
                                    .map(fe -> fe.getField() + " " + fe.getDefaultMessage()));
                })
                .onError(ServerWebInputException.class, (e, req) -> {
                    log.error(e.getCause().getMessage(), e);
                    return ServerResponse.badRequest()
                            .bodyValue(e.getCause().getMessage());
                })
                .onError(Exception.class, (e, req) -> {
                    log.error(e.getMessage(), e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                })
                .build();
    }
}
