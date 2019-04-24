package me.yangbajing.springreactive.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SexType {
    MEN(1, "男"),
    WOMEN(2, "女"),
    ;
    @JsonValue
    private Integer value;
    private String name;

    SexType(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }
}
