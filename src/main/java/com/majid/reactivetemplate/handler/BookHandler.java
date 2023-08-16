package com.majid.reactivetemplate.handler;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.mapper.BookMapper;
import com.majid.reactivetemplate.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BookHandler {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public Mono<ServerResponse> saveBook(ServerRequest request) {
        return request
                .bodyToMono(BookDto.class)
                .map(bookMapper::fromDto)
                .flatMap(bookService::save)
                .map(bookMapper::toDto)
                .transform(bookDto ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(bookDto, BookDto.class));
    }
}
