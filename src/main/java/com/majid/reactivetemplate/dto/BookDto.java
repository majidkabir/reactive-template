package com.majid.reactivetemplate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "cannot be blank")
    private String title;
    private String author;
    private String publisher;
    private String description;
}
