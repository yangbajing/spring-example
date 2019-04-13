package me.yangbajing.springreactive.web.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.yangbajing.springreactive.data.sign.SignupDTO;
import me.yangbajing.springreactive.data.sign.result.ApiResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CredentialControllerTest {
    private final WebTestClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    CredentialControllerTest(WebTestClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    @Test
    void signup() throws JsonProcessingException {
        SignupDTO signupDTO = new SignupDTO();
        EntityExchangeResult<ApiResult> response = webClient.post().uri("/api/credential/signup")
                .syncBody(signupDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ApiResult.class)
                .returnResult();
        ApiResult result = response.getResponseBody();
        log.debug("Signup result is {}", objectMapper.writeValueAsString(result));
        assertNotNull(result);
    }

}