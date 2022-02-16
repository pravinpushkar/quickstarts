package com.service;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OrderProcessingServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(OrderProcessingServiceApplication.class);
    private static final String SECRET_STORE_NAME = "localsecretstore";

    public static void main(String[] args) throws InterruptedException {
        DaprClient client = new DaprClientBuilder().build();
        Map<String, String> secret = client.getSecret(SECRET_STORE_NAME, "mysecret").block();
        log.info(secret.toString());
    }
}
