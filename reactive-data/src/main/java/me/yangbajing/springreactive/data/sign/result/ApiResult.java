package me.yangbajing.springreactive.data.sign.result;

import me.yangbajing.springreactive.enums.StatusEnum;

public final class ApiResult {
    private int status;
    private String message;
    private Object data;

    private ApiResult() {
    }

    public ApiResult(int status, String message, Object data) {
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
        return new ApiResult(status.getValue(), message, null);
    }

    public static ApiResult ok(Object data, StatusEnum status) {
        return new ApiResult(status.getValue(), null, data);
    }

    public static ApiResult error(StatusEnum status) {
        return error(status, null);
    }

    private static ApiResult error(StatusEnum status, String message) {
        return error(status, message, null);
    }

    private static ApiResult error(StatusEnum status, String message, Object data) {
        return new ApiResult(status.getValue(), message, data);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public ApiResult setStatus(int status) {
        this.status = status;
        return this;
    }
    public ApiResult setStatus(StatusEnum status) {
        setStatus(status.getValue());
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
