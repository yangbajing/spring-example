package me.yangbajing.springreactive.business.service;

import lombok.extern.slf4j.Slf4j;
import me.yangbajing.springreactive.data.sign.SigninDTO;
import me.yangbajing.springreactive.data.sign.SignBO;
import me.yangbajing.springreactive.data.sign.SignupDTO;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CredentialService {
    public SignBO signup(SignupDTO signupDTO) {
        log.info("signup: {}", signupDTO);
        return SignBO.builder().loginEmail(signupDTO.getAccount()).build();
    }

    public SignBO signip(SigninDTO signinDTO) {
        log.info("signip: {}", signinDTO);
        return SignBO.builder().loginEmail(signinDTO.getAccount()).build();
    }
}
