package com.bridge.impalaswitch;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

public class SwitchRequest {
    protected Builder builder;

    public SwitchRequest() {
        this.configureBuilder();
    }

    private void configureBuilder() {
        try {
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();

            HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
            this.builder = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient))
                    .exchangeStrategies(ExchangeStrategies.builder().codecs(configurer -> {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        configurer.customCodecs().decoder(
                                new Jackson2JsonDecoder(mapper,
                                        MimeTypeUtils.parseMimeType(MediaType.TEXT_PLAIN_VALUE)));
                    }).build());
        } catch (Exception e) {

        }
    }
}
