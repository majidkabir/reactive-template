package com.majid.reactivetemplate.route;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.handler.BookHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
        @RouterOperation(
                path = "/book",
                method = RequestMethod.POST,
                beanClass = BookHandler.class,
                beanMethod = "saveBook",
                operation = @Operation(
                        operationId = "event",
                        description = "Storing the audit event",
                        tags = "book",
                        requestBody =
                                @RequestBody(
                                        description = "Book to add",
                                        required = true,
                                        content = @Content(
                                                schema = @Schema(
                                                        implementation = BookDto.class,

                                                        requiredProperties = {"title", "author"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Add book response",
                                        content = @Content(
                                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                schema = @Schema(implementation = BookDto.class)))
                        }))
})

public @interface ApiInfo {}
