package me.yangbajing.springreactive.data.sign.result;

import me.yangbajing.springreactive.enums.StatusEnum;

public final class ApiResult {
    private StatusEnum status;
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

    public StatusEnum getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public ApiResult setStatus(StatusEnum status) {
        this.status = status;
        return this;
    }

    public ApiResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public ApiResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
