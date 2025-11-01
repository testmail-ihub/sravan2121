package com.example.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NewApiClientTest {

    private NewApiClient newApiClient;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() {
        mockHttpClient = Mockito.mock(HttpClient.class);
        mockHttpResponse = Mockito.mock(HttpResponse.class);
        newApiClient = new NewApiClient("http://test.com") {
            @Override
            protected HttpClient getHttpClient() {
                return mockHttpClient;
            }
        };
    }

    @Test
    void getData_shouldReturnBody() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandlers.ofString().getClass())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn("test data");

        String result = newApiClient.getData("/data");
        assertEquals("test data", result);
    }

    @Test
    void postData_shouldReturnBody() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandlers.ofString().getClass())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn("post success");

        String result = newApiClient.postData("/post", "{}");
        assertEquals("post success", result);
    }
}