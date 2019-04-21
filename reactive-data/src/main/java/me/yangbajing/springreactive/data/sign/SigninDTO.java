package me.yangbajing.springreactive.data.sign;

import lombok.Data;
import me.yangbajing.springreactive.util.CheckUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SigninDTO {
    @NotNull(message = "账号不能为空")
    private String account;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, message = "密码最少6个字符")
    private String password;

    public boolean hasEmail() {
        return CheckUtils.isEmail(account);
    }

    public boolean hasPhone() {
        return CheckUtils.isPhone(account);
    }
}
