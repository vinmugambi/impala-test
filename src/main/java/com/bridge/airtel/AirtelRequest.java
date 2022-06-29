package com.bridge.airtel;

import org.springframework.web.reactive.function.client.WebClient;

public class AirtelRequest {
    protected WebClient client;

    public AirtelRequest() {
        this.client = WebClient.builder().baseUrl("https://openapiuat.airtel.africa/").build();
    }
}
