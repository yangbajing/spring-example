package me.yangbajing.springreactive.data.user;

import lombok.Builder;
import lombok.Data;
import me.yangbajing.springreactive.enums.StatusEnum;

@Data
@Builder
public class UserEntity {
    private String id;
    private String name;
    private StatusEnum status;
}
