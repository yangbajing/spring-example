package me.yangbajing.springreactive.web.controller.api;

import me.yangbajing.springreactive.data.Hello;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureWebTestClient
class HelloControllerTest {
    private final WebTestClient webClient;

    @Autowired
    HelloControllerTest(WebTestClient webClient) {
        this.webClient = webClient;
    }

    @Test
    void world() {
        EntityExchangeResult<Hello> response = webClient.get()
                .uri(builder ->
                        builder.path("/api/hello/world")
                                .queryParam("hello", "您好")
                                .queryParam("world", "Spring 5")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hello.class)
                .returnResult();
        Hello hello = response.getResponseBody();
        assertNotNull(hello);
        assertEquals(hello.getHello(), "您好");
        assertEquals(hello.getWorld(), "Spring 5");
    }

}