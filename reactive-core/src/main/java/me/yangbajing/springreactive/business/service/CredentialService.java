package me.yangbajing.springreactive.business.service;

import lombok.extern.slf4j.Slf4j;
import me.yangbajing.springreactive.data.sign.SignupBO;
import me.yangbajing.springreactive.data.sign.SignupDTO;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CredentialService {
    public SignupBO signup(SignupDTO signupDTO) {
        log.info("signup: {}", signupDTO);
        return SignupBO.builder().loginEmail(signupDTO.getAccount()).build();
    }
}
