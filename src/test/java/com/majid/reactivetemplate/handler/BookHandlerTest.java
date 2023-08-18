package com.majid.reactivetemplate.handler;

import com.majid.reactivetemplate.mapper.BookMapper;
import com.majid.reactivetemplate.model.Book;
import com.majid.reactivetemplate.route.RouterConfig;
import com.majid.reactivetemplate.service.BookService;
import com.majid.reactivetemplate.validation.ValidatorConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.majid.reactivetemplate.TestUtil.mockSaveToDB;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@WebFluxTest
@Import({RouterConfig.class, BookHandler.class, BookMapper.class, ValidatorConfig.class})
class BookHandlerTest {

    @MockBean
    BookService bookService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void saveBook() {
        doAnswer(mockSaveToDB())
                .when(bookService).save(any());

        webTestClient.post()
                .uri("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("""
                        {
                            "title":"Symphony of the Dead",
                            "author": "Abbas Maroufi",
                            "publisher": "Qoqnoos Publishing Group",
                            "description": "The story is told in symphonic form."
                        }
                        """)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("title").isEqualTo("Symphony of the Dead")
                .jsonPath("author").isEqualTo("Abbas Maroufi")
                .jsonPath("publisher").isEqualTo("Qoqnoos Publishing Group")
                .jsonPath("description").isEqualTo("The story is told in symphonic form.");
    }

    @Test
    void findBook() {
        Book book = new Book("Symphony of the Dead", "Abbas Maroufi", "Qoqnoos Publishing Group", "The story is told in symphonic form.");
        book.setId(1L);
        doReturn(Mono.just(book))
                .when(bookService).findById(1L);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book/{id}")
                        .build(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("title").isEqualTo("Symphony of the Dead")
                .jsonPath("author").isEqualTo("Abbas Maroufi")
                .jsonPath("publisher").isEqualTo("Qoqnoos Publishing Group")
                .jsonPath("description").isEqualTo("The story is told in symphonic form.");
    }

    @Test
    void findBooks() {
        Book book = new Book("Symphony of the Dead", "Abbas Maroufi", "Qoqnoos Publishing Group", "The story is told in symphonic form.");
        book.setId(1L);
        doReturn(Flux.just(book))
                .when(bookService).findByExample(any());

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book")
                        .queryParam("title", "Symphony of the Dead")
                        .queryParam("author", "Abbas Maroufi")
                        .queryParam("publisher", "Qoqnoos Publishing Group")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].title").isEqualTo("Symphony of the Dead")
                .jsonPath("$[0].author").isEqualTo("Abbas Maroufi")
                .jsonPath("$[0].publisher").isEqualTo("Qoqnoos Publishing Group")
                .jsonPath("$[0].description").isEqualTo("The story is told in symphonic form.");
    }
}
