package me.yangbajing.springreactive.business.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.yangbajing.springreactive.data.sign.result.ApiResult;
import me.yangbajing.springreactive.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@Component
public class HttpComponent {
    private final ObjectMapper objectMapper;

    @Autowired
    public HttpComponent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Mono<ApiResult> justApiResult(Throwable t) {
        ApiResult result = ApiResult.error(StatusEnum.INTERNAL_SERVER_ERROR);
        if (t instanceof WebExchangeBindException) {
            result.setStatus(StatusEnum.BAD_REQUEST);
            WebExchangeBindException e = (WebExchangeBindException) t;
            ObjectNode data = objectMapper.createObjectNode();
            e.getFieldErrors().forEach(field -> data.put(field.getField(), field.getDefaultMessage()));
            result.setData(data);
        } else {
            result.setMessage(t.getLocalizedMessage());
        }
        return Mono.just(result);
    }

    public ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public ObjectNode asObjectNode(Object value) {
        return objectMapper.valueToTree(value);
    }

}
