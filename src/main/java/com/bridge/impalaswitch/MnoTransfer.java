package com.bridge.impalaswitch;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class MnoTransfer extends SwitchRequest {

    public MnoTransfer() {
        super();
    }

    private Logger logger = LoggerFactory.getLogger(GetAuth.class);

    private Mono<MnoTransferSucessDto> doPost(String sessionId) {
        HashMap requestBody = new HashMap();
        requestBody.put("api_username", "mugambi");
        requestBody.put("session_id", sessionId);
        requestBody.put("amount", 50);
        requestBody.put("source_country_code", "UK");
        requestBody.put("sendername", "Vincent Mugambi");
        requestBody.put("recipient_mobile", "254703784709");
        requestBody.put("recipient_currency_code", "KES");
        requestBody.put("recipient_country_code", "KE");
        requestBody.put("reference_number", "DONOTTOUCHHERE");
        requestBody.put("sendertoken", "254703784709");
        requestBody.put("client_datetime", "2021-07-25T15:54:45+02:00");
        requestBody.put("originate_currency", "KES");

        WebClient client = this.builder
                .baseUrl("https://staging.impalapay.net").build();

        return client.post()
                .uri("/mnoTransfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(MnoTransferSucessDto.class);
    }

    public String send(String sessionId) throws Exception {
        try {
            Mono<MnoTransferSucessDto> request = this.doPost(sessionId);
            MnoTransferSucessDto res = request.share().block();

            if (res.transaction_id != null) {
                return res.transaction_id;
            }
            throw new Error(res.command_status);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

}

// {
// "api_username": "mugambi",
// "session_id": "d359cbe4b5c143c1b0ff3d88cdac8f38",
// "source_country_code": "KE",
// "sendername": "Eugene Chimita",
// "recipient_mobile": "254715290374",
// "amount": 40.78,
// "recipient_currency_code": "KES",
// "recipient_country_code": "KE",
// "reference_number": "OPIEIONUI02392",
// "sendertoken": "254783489290",
// "client_datetime": "2021-07-25T15:54:45+02:00",
// "originate_currency": "KES"
// }