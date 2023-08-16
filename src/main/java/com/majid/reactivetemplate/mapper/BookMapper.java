package com.majid.reactivetemplate.mapper;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getDescription());
    }

    public Book fromDto(BookDto dto) {
        return new Book(
                dto.getTitle(),
                dto.getAuthor(),
                dto.getPublisher(),
                dto.getDescription());
    }
}
