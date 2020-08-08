package me.yangbajing.springreactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.yangbajing.springreactive.data.sign.result.ApiResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Slf4j
public class EnumTest {
    private ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    public void test() throws InvocationTargetException, IllegalAccessException, IOException {
        ApiResult result = ApiResult.ok();
        String str = objectMapper.writeValueAsString(result);
        log.info("json str {}", str);
        ApiResult obj = objectMapper.readValue(str, ApiResult.class);
        log.info("obj {}", obj);
    }

}
