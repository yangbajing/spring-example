package me.yangbajing.springreactive.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum implements IEnum<Integer> {
    OK(200, "Ok"),

    CREATED(201, "Created"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    @JsonValue
    private Integer value;
    private String message;

    StatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
