package com.majid.reactivetemplate.handler;

import com.majid.reactivetemplate.mapper.BookMapper;
import com.majid.reactivetemplate.route.RouterConfig;
import com.majid.reactivetemplate.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.majid.reactivetemplate.TestUtil.mockSaveToDB;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@WebFluxTest
@Import({RouterConfig.class, BookHandler.class, BookMapper.class})
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
}
