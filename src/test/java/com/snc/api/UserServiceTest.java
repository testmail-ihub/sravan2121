package com.snc.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private BaseApiClient mockApiClient;
    private final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        mockApiClient = Mockito.mock(BaseApiClient.class);
        userService = new UserService(BASE_URL) {
            // Override the apiClient to inject the mock
            @Override
            protected BaseApiClient getApiClient() {
                return mockApiClient;
            }
        };
    }

    @Test
    void testCreateUser() throws IOException, InterruptedException {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "John Doe");
        userData.put("email", "john.doe@example.com");

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(201);
        when(mockResponse.body()).thenReturn("User created successfully");

        when(mockApiClient.sendPostRequest(eq("/users"), anyString()))
                .thenReturn(mockResponse);

        HttpResponse<String> response = userService.createUser(userData);

        assertEquals(201, response.statusCode());
        assertEquals("User created successfully", response.body());
    }

    @Test
    void testGetUser() throws IOException, InterruptedException {
        String userId = "123";
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("User details for 123");

        when(mockApiClient.sendGetRequest(eq("/users/" + userId)))
                .thenReturn(mockResponse);

        HttpResponse<String> response = userService.getUser(userId);

        assertEquals(200, response.statusCode());
        assertEquals("User details for 123", response.body());
    }

    @Test
    void testGetAllUsers() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("All users list");

        when(mockApiClient.sendGetRequest(eq("/users")))
                .thenReturn(mockResponse);

        HttpResponse<String> response = userService.getAllUsers();

        assertEquals(200, response.statusCode());
        assertEquals("All users list", response.body());
    }

    @Test
    void testUpdateUser() throws IOException, InterruptedException {
        String userId = "123";
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", "Jane Doe");

        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("User updated successfully");

        when(mockApiClient.sendPutRequest(eq("/users/" + userId), anyString()))
                .thenReturn(mockResponse);

        HttpResponse<String> response = userService.updateUser(userId, userData);

        assertEquals(200, response.statusCode());
        assertEquals("User updated successfully", response.body());
    }

    @Test
    void testDeleteUser() throws IOException, InterruptedException {
        String userId = "123";
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(204);
        when(mockResponse.body()).thenReturn("");

        when(mockApiClient.sendDeleteRequest(eq("/users/" + userId)))
                .thenReturn(mockResponse);

        HttpResponse<String> response = userService.deleteUser(userId);

        assertEquals(204, response.statusCode());
        assertEquals("", response.body());
    }
}