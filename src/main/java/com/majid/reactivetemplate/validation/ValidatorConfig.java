package com.majid.reactivetemplate.validation;

import com.majid.reactivetemplate.dto.BookDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

@Configuration
public class ValidatorConfig {

    @Bean
    public ValidationHandler<BookDto> bookValidationHandler(Validator validator) {
        return new ValidationHandler<>(validator, BookDto.class);
    }

}
