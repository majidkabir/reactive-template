package com.majid.reactivetemplate.mapper;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book fromDto(BookDto dto);
}
