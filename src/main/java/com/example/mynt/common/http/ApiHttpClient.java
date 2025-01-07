package com.example.mynt.common.http;

import com.example.mynt.common.exception.CostDeliveryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ApiHttpClient {

    private final HttpClient httpClient;

    private static final int CLIENT_SERVER_ERROR = 400;

    @Autowired
    public ApiHttpClient() {
        this.httpClient = HttpClient.newHttpClient();
    }
    public String get(String url) throws CostDeliveryException {
        HttpResponse<String> response;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (statusCode >= CLIENT_SERVER_ERROR) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            throw new CostDeliveryException(ex.getMessage(), ex);
        }
        return response.body();
    }
}
