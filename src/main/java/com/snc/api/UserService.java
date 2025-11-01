package com.snc.api;

import java.io.IOException;
import java.net.http.HttpResponse;

public class UserService {

    private final BaseApiClient apiClient;
    private final String usersEndpoint = "/users";

    public UserService(BaseApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public HttpResponse<String> getAllUsers() throws IOException, InterruptedException {
        return apiClient.sendGetRequest(usersEndpoint);
    }

    public HttpResponse<String> getUserById(String id) throws IOException, InterruptedException {
        return apiClient.sendGetRequest(usersEndpoint + "/" + id);
    }

    public HttpResponse<String> createUser(String userJson) throws IOException, InterruptedException {
        return apiClient.sendPostRequest(usersEndpoint, userJson);
    }

    public HttpResponse<String> updateUser(String id, String userJson) throws IOException, InterruptedException {
        return apiClient.sendPutRequest(usersEndpoint + "/" + id, userJson);
    }

    public HttpResponse<String> deleteUser(String id) throws IOException, InterruptedException {
        return apiClient.sendDeleteRequest(usersEndpoint + "/" + id);
    }
}