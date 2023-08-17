package com.majid.reactivetemplate.handler;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.mapper.BookMapper;
import com.majid.reactivetemplate.model.Book;
import com.majid.reactivetemplate.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
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
                .transform(bookDtoMono ->
                        ServerResponse
                                .status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(bookDtoMono, BookDto.class));
    }

    public Mono<ServerResponse> findBook(ServerRequest request) {
        return bookService
                .findById(Long.parseLong(request.pathVariable("id")))
                .map(bookMapper::toDto)
                .transform(book ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(book, BookDto.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findBooks(ServerRequest request) {
        Book book = new Book();
        request.queryParam("title")
                .ifPresent(book::setTitle);
        request.queryParam("author")
                .ifPresent(book::setAuthor);
        request.queryParam("publisher")
                .ifPresent(book::setPublisher);

        Flux<BookDto> bookDtoFlux = bookService
                .findByExample(book)
                .map(bookMapper::toDto);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookDtoFlux, BookDto.class);
    }
}
