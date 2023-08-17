package com.majid.reactivetemplate.route;

import com.majid.reactivetemplate.handler.BookHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
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
                .build();
    }
}
