package com.majid.reactivetemplate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.util.List;

@Getter
@AllArgsConstructor
public class InvalidParameterException extends Exception {

    @Serial
    private static final long serialVersionUID = -1900414231151323879L;

    private final List<FieldError> errors;
}
