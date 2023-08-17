package com.majid.reactivetemplate.service;

import com.majid.reactivetemplate.model.Book;
import com.majid.reactivetemplate.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Flux<Book> findByExample(Book book) {
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreCase()
                .withMatcher("title", contains())
                .withMatcher("author", contains())
                .withMatcher("publisher", contains());

        return bookRepository.findAll(Example.of(book, matcher));
    }
}
