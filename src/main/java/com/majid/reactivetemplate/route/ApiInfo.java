package com.majid.reactivetemplate.route;

import com.majid.reactivetemplate.dto.BookDto;
import com.majid.reactivetemplate.handler.BookHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
                        operationId = "saveBook",
                        description = "Storing the book information",
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
                        })),
        @RouterOperation(
                path = "/book/{id}",
                method = RequestMethod.GET,
                beanClass = BookHandler.class,
                beanMethod = "findBook",
                operation = @Operation(
                        operationId = "findBook",
                        description = "Finding a book by ID",
                        tags = "book",
                        parameters = {
                                @Parameter(
                                        name = "id",
                                        description = "Unique ID of a book in the system.",
                                        in = ParameterIn.PATH,
                                        required = true)
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Book information",
                                        content = @Content(
                                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                schema = @Schema(implementation = BookDto.class)))
                        })),
        @RouterOperation(
                path = "/book",
                method = RequestMethod.GET,
                beanClass = BookHandler.class,
                beanMethod = "findBooks",
                operation = @Operation(
                        operationId = "findBooks",
                        description = "Querying all books",
                        tags = "book",
                        parameters = {
                                @Parameter(
                                        name = "title",
                                        description = "Title of the book",
                                        in = ParameterIn.QUERY),
                                @Parameter(
                                        name = "author",
                                        description = "Author of the book",
                                        in = ParameterIn.QUERY),
                                @Parameter(
                                        name = "publisher",
                                        description = "Publisher of the book",
                                        in = ParameterIn.QUERY)
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Book information",
                                        content = @Content(
                                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                array = @ArraySchema(schema = @Schema(implementation = BookDto.class))))
                        }))
})

public @interface ApiInfo {}
