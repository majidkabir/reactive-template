package com.majid.reactivetemplate.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@AutoConfigureWebTestClient
@SpringBootTest
@Testcontainers
class ReactiveTemplateApplicationTests {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgresql =
			new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.4"))
					.withDatabaseName("book-service-db")
					.waitingFor(Wait.forListeningPort());

	@Autowired
	WebTestClient webTestClient;

	@Test
	void saveBook() {
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
