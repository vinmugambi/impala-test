package com.bridge.airtel;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Mono;

public class GetAuth extends AirtelRequest {
    public GetAuth() {
        super();
    }

    private Logger logger = LoggerFactory.getLogger(GetAuth.class);

    public Mono<AuthSuccessDTO> doPost() {
        HashMap<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("client_id", "b435e1a7-04cf-455c-9151-555a84331011");
        requestBody.put("client_secret", "5047b004-6ed7-487c-9384-1a90b509afca");

        return this.client.post().uri("/auth/oauth2/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(AuthSuccessDTO.class);
    }

    public String send() throws Exception {
        try {
            Mono<AuthSuccessDTO> res = doPost();
            AuthSuccessDTO resBody = res.share().block();
            return resBody.access_token;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}

// {
// "client_id": "*****************************",
// "client_secret": "*****************************",
// "grant_type": "client_credentials"
// }

// {
// "access_token": "*****************************",
// "expires_in": 7200,
// "token_type": "bearer"
// }
