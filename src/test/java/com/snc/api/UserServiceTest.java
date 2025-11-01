package com.snc.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private BaseApiClient mockApiClient;
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockApiClient = Mockito.mock(BaseApiClient.class);
        userService = new UserService(mockApiClient);
    }

    @Test
    void testGetAllUsers() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockApiClient.sendGetRequest(anyString())).thenReturn(mockResponse);
        HttpResponse<String> response = userService.getAllUsers();
        assertEquals(mockResponse, response);
    }

    @Test
    void testGetUserById() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockApiClient.sendGetRequest(anyString())).thenReturn(mockResponse);
        HttpResponse<String> response = userService.getUserById("123");
        assertEquals(mockResponse, response);
    }

    @Test
    void testCreateUser() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockApiClient.sendPostRequest(anyString(), anyString())).thenReturn(mockResponse);
        HttpResponse<String> response = userService.createUser("{ \"name\": \"test\" }");
        assertEquals(mockResponse, response);
    }

    @Test
    void testUpdateUser() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockApiClient.sendPutRequest(anyString(), anyString())).thenReturn(mockResponse);
        HttpResponse<String> response = userService.updateUser("123", "{ \"name\": \"updated\" }");
        assertEquals(mockResponse, response);
    }

    @Test
    void testDeleteUser() throws IOException, InterruptedException {
        HttpResponse<String> mockResponse = Mockito.mock(HttpResponse.class);
        when(mockApiClient.sendDeleteRequest(anyString())).thenReturn(mockResponse);
        HttpResponse<String> response = userService.deleteUser("123");
        assertEquals(mockResponse, response);
    }
}