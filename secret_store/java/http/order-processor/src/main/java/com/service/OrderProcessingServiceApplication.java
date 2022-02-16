package com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class OrderProcessingServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessingServiceApplication.class);

    private static final String DAPR_HOST = System.getenv().getOrDefault("DAPR_HOST", "http://localhost");
    private static final String DAPR_HTTP_PORT = System.getenv().getOrDefault("DAPR_HTTP_PORT", "3500");
    private static final String SECRET_STORE_NAME = System.getenv().getOrDefault("SECRET_STORE", "localsecretstore");
    private static final String SECRET_NAME = "mysecret";
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        URI secretStoreURI = new URI(DAPR_HOST+":"+DAPR_HTTP_PORT+"/v1.0/secrets/"+SECRET_STORE_NAME + "/"+SECRET_NAME);
        System.out.println("secret store get.... " + secretStoreURI.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(secretStoreURI)
                .build();
        HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        LOGGER.info(response.body().toString());
    }
}
