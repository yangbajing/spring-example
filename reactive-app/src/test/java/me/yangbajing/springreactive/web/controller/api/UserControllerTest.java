package me.yangbajing.springreactive.web.controller.api;

import me.yangbajing.springreactive.data.sign.result.ApiResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureWebTestClient
class UserControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    void findFromSession() {
        EntityExchangeResult<ApiResult> response = webClient.get().uri("/api/user")
                .cookie("token", "session.token")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ApiResult.class)
                .returnResult();
        ApiResult result = response.getResponseBody();
        assertNotNull(result);
        assertEquals(result.getData(), "session.token");
    }

}