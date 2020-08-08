package me.yangbajing.springreactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.yangbajing.springreactive.data.user.UserEntity;
import me.yangbajing.springreactive.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
class ApplicationTest {
    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void main() throws IOException {
        String ids = objectMapper.getRegisteredModuleIds().stream().map(Object::toString).collect(Collectors.joining(","));
        log.info("ids: {}", ids);

        UserEntity user = UserEntity.builder()
                .status(StatusEnum.CREATED)
                .build();
        String userString = objectMapper.writeValueAsString(user);
        log.info("user json string: {}", userString);
        UserEntity u = objectMapper.readValue(userString, UserEntity.class);
        log.info("user object: {}", u);
        assertEquals(user.getStatus(), u.getStatus());
    }
}