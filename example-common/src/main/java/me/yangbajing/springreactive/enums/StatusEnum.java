package me.yangbajing.springreactive.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum /*implements IEnum<Integer>*/ {
    OK(200, "Ok"),

    CREATED(201, "Created"),

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;

    private Integer value;
    private String message;

    StatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
