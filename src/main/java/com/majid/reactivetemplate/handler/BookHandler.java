package com.majid.reactivetemplate.handler;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.mapper.BookMapper;
import com.majid.reactivetemplate.model.Book;
import com.majid.reactivetemplate.service.BookService;
import com.majid.reactivetemplate.validation.ValidationHandler;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
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

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    private final BookService bookService;
    private final ValidationHandler<BookDto> bookValidation;

    public Mono<ServerResponse> saveBook(ServerRequest request) {
        return bookValidation.handle(request, validatedDto ->
            bookService
                    .save(bookMapper.fromDto(validatedDto))
                    .map(bookMapper::toDto)
                    .flatMap(bookDto ->
                            ServerResponse
                                    .status(HttpStatus.CREATED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(bookDto)
                    ));
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
