package me.yangbajing.springreactive.data.sign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupBO {
    private String id;
    private String loginEmail;
    private String loginPhone;
}
