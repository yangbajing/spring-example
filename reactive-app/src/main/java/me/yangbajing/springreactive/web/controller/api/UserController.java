package me.yangbajing.springreactive.web.controller.api;

import me.yangbajing.springreactive.data.sign.result.ApiResult;
import me.yangbajing.springreactive.enums.StatusEnum;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    @GetMapping
    public ApiResult findFromSession(ServerHttpRequest request) {
        HttpCookie tokenCookie = request.getCookies().getFirst("token");
        String tokenHeader = request.getHeaders().getFirst("token");
        if (Objects.isNull(tokenCookie)) {
            return ApiResult.error(StatusEnum.UNAUTHORIZED);
        }

        return ApiResult.ok(tokenCookie.getValue());
    }

}
