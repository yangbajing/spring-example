package me.yangbajing.springreactive.enums;

public enum StatusEnum {
    OK(200, "Ok"),
    CREATED(201, "Created"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private int status;
    private String message;

    StatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
