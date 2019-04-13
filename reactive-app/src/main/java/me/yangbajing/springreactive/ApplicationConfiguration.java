package me.yangbajing.springreactive;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
public class ApplicationConfiguration implements WebFluxConfigurer {
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
//        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }
}
