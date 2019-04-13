package me.yangbajing.springreactive.web.controller.api;

import me.yangbajing.springreactive.business.component.HttpComponent;
import me.yangbajing.springreactive.business.service.CredentialService;
import me.yangbajing.springreactive.data.sign.SignupDTO;
import me.yangbajing.springreactive.data.sign.result.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/credential")
public class CredentialController {
    private final HttpComponent httpComponent;
    private final CredentialService credentialService;

    @Autowired
    public CredentialController(HttpComponent httpComponent, CredentialService credentialService) {
        this.httpComponent = httpComponent;
        this.credentialService = credentialService;
    }

    @PostMapping(path = "signup")
    public Mono<ApiResult> signup(@Valid @RequestBody Mono<SignupDTO> mono) {
        return mono
                .map(signupDTO -> ApiResult.ok(credentialService.signup(signupDTO)))
                .onErrorResume(httpComponent::justApiResult);
    }
}
