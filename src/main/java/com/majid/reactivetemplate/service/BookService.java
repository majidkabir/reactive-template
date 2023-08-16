package com.majid.reactivetemplate.service;

import com.majid.reactivetemplate.model.Book;
import com.majid.reactivetemplate.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}
