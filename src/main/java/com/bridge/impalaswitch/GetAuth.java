package com.bridge.impalaswitch;

import java.util.HashMap;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class GetAuth extends SwitchRequest {

    public GetAuth() {
        super();
    }

    private Logger logger = LoggerFactory.getLogger(GetAuth.class);

    public Mono<SessionSuccessDto> doPost(LoginFormDto loginForm) throws SSLException {

        HashMap<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("api_password", loginForm.password);
        requestBody.put("api_username", loginForm.username);

        WebClient client = this.builder
                .baseUrl("https://staging.impalapay.net:8454").build();

        return client.post()
                .uri("/specialsessionid")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(SessionSuccessDto.class);

    }

    public String send(LoginFormDto loginForm) throws Exception {
        try {
            Mono<SessionSuccessDto> res = this.doPost(loginForm);
            SessionSuccessDto sessionSuccessDto = res.share().block();
            return sessionSuccessDto.session_id;

        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new Exception("Authentication failed");
        }
    }
}
