package com.majid.reactivetemplate.validation;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface DtoHandlerFunction<U, T extends ServerResponse> {
    Mono<T> handle(U dto);
}
