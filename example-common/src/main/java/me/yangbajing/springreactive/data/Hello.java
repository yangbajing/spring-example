package me.yangbajing.springreactive.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hello {
    private String hello;
    private String world;
}
