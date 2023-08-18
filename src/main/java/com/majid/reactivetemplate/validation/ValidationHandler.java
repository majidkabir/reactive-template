package com.majid.reactivetemplate.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ValidationHandler<T> {

    private final Validator validator;

    private final Class<T> dtoClass;

    public Mono<ServerResponse> handle(ServerRequest request, DtoHandlerFunction<T, ServerResponse> next) {
        return request.bodyToMono(this.dtoClass)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, this.dtoClass.getName());
                    this.validator.validate(dto, errors);

                    if (errors.hasFieldErrors()) {
                        return ServerResponse.badRequest().bodyValue(
                                errors.getFieldErrors().stream()
                                        .map(e -> e.getField() + " " + e.getDefaultMessage()));
                    }

                    return next.handle(dto);
                });
    }
}
