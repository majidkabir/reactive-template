package com.majid.reactivetemplate.mapper;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookDto toDto(Book book);
    Book fromDto(BookDto dto);
}
