package me.yangbajing.springreactive.data.sign.result;

import lombok.Data;
import me.yangbajing.springreactive.enums.SexType;
import me.yangbajing.springreactive.enums.StatusEnum;

@Data
public final class ApiResult {
    private StatusEnum status;
    private SexType sex = SexType.MEN;
    private String message;
    private Object data;

    private ApiResult() {
    }

    public ApiResult(StatusEnum status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ApiResult ok() {
        return ok(null);
    }

    public static ApiResult ok(Object data) {
        return ok(data, StatusEnum.OK);
    }

    public static ApiResult ok(StatusEnum status, String message) {
        return new ApiResult(status, message, null);
    }

    public static ApiResult ok(Object data, StatusEnum status) {
        return new ApiResult(status, null, data);
    }

    public static ApiResult error(StatusEnum status) {
        return error(status, null);
    }

    private static ApiResult error(StatusEnum status, String message) {
        return new ApiResult(status, message, null);
    }
}
