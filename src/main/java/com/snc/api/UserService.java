package com.snc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class UserService {

    private final BaseApiClient apiClient;
    private final ObjectMapper objectMapper;
    private static final String USERS_ENDPOINT = "/users";

    public UserService(String baseUrl) {
        this.apiClient = new BaseApiClient(baseUrl);
        this.objectMapper = new ObjectMapper();
    }

    public HttpResponse<String> createUser(Map<String, Object> userData) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(userData);
        return apiClient.sendPostRequest(USERS_ENDPOINT, requestBody);
    }

    public HttpResponse<String> getUser(String userId) throws IOException, InterruptedException {
        return apiClient.sendGetRequest(USERS_ENDPOINT + "/" + userId);
    }

    public HttpResponse<String> getAllUsers() throws IOException, InterruptedException {
        return apiClient.sendGetRequest(USERS_ENDPOINT);
    }

    public HttpResponse<String> updateUser(String userId, Map<String, Object> userData) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(userData);
        return apiClient.sendPutRequest(USERS_ENDPOINT + "/" + userId, requestBody);
    }

    public HttpResponse<String> sendDeleteRequest(String userId) throws IOException, InterruptedException {
        return apiClient.sendDeleteRequest(USERS_ENDPOINT + "/" + userId);
    }
}