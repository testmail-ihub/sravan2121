import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserApiClientTest {

    private UserApiClient userApiClient;
    private HttpClient mockHttpClient;
    private final String baseUrl = "https://api.example.com";

    @BeforeEach
    void setUp() {
        mockHttpClient = Mockito.mock(HttpClient.class);
        userApiClient = new UserApiClient(baseUrl) {
            @Override
            protected HttpClient getHttpClient() {
                return mockHttpClient;
            }
        };
    }

    @Test
    void getUser_success() throws ExecutionException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\": 101, \"name\": \"John Doe\"}");
        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        String response = userApiClient.getUser(101).get();
        assertEquals("{\"id\": 101, \"name\": \"John Doe\"}", response);
        verify(mockHttpClient, times(1)).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void getUser_4xxError() throws ExecutionException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(404);
        when(mockResponse.body()).thenReturn("{\"error\": \"Not Found\"}");
        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        ExecutionException thrown = assertThrows(ExecutionException.class, () -> userApiClient.getUser(101).get());
        assertTrue(thrown.getMessage().contains("API Error: 404"));
        verify(mockHttpClient, times(1)).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void getUser_5xxError_retries() throws ExecutionException, InterruptedException {
        HttpResponse<String> mockResponse500 = mock(HttpResponse.class);
        when(mockResponse500.statusCode()).thenReturn(500);
        when(mockResponse500.body()).thenReturn("{\"error\": \"Internal Server Error\"}");

        HttpResponse<String> mockResponse200 = mock(HttpResponse.class);
        when(mockResponse200.statusCode()).thenReturn(200);
        when(mockResponse200.body()).thenReturn("{\"id\": 101, \"name\": \"John Doe\"}");

        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse500))
                .thenReturn(CompletableFuture.completedFuture(mockResponse500))
                .thenReturn(CompletableFuture.completedFuture(mockResponse500))
                .thenReturn(CompletableFuture.completedFuture(mockResponse200)); // This should not be reached if retries are 3

        ExecutionException thrown = assertThrows(ExecutionException.class, () -> userApiClient.getUser(101).get());
        assertTrue(thrown.getMessage().contains("API Error: 500"));
        verify(mockHttpClient, times(3)).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void getUser_timeout_retries() throws ExecutionException, InterruptedException {
        CompletableFuture<HttpResponse<String>> timeoutFuture = new CompletableFuture<>();
        timeoutFuture.completeExceptionally(new java.net.http.HttpTimeoutException("Timeout"));

        HttpResponse<String> mockResponse200 = mock(HttpResponse.class);
        when(mockResponse200.statusCode()).thenReturn(200);
        when(mockResponse200.body()).thenReturn("{\"id\": 101, \"name\": \"John Doe\"}");

        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(timeoutFuture)
                .thenReturn(timeoutFuture)
                .thenReturn(timeoutFuture)
                .thenReturn(CompletableFuture.completedFuture(mockResponse200)); // This should not be reached if retries are 3

        ExecutionException thrown = assertThrows(ExecutionException.class, () -> userApiClient.getUser(101).get());
        assertTrue(thrown.getMessage().contains("Request failed: Timeout"));
        verify(mockHttpClient, times(3)).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void createUser_success() throws ExecutionException, InterruptedException {
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(201);
        when(mockResponse.body()).thenReturn("{\"status\": \"success\", \"message\": \"User created\"}");
        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        String jsonBody = "{\"id\": 101, \"name\": \"John Doe\"}";
        String response = userApiClient.createUser(jsonBody).get();
        assertEquals("{\"status\": \"success\", \"message\": \"User created\"}", response);
        verify(mockHttpClient, times(1)).sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void loggingVerification() throws ExecutionException, InterruptedException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"id\": 101, \"name\": \"John Doe\"}");
        when(mockHttpClient.sendAsync(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(CompletableFuture.completedFuture(mockResponse));

        userApiClient.getUser(101).get();

        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Request URL:"));
        assertTrue(consoleOutput.contains("Response Status Code: 200"));

        System.setOut(System.out); // Reset System.out
    }

    // Helper to allow mocking HttpClient in UserApiClient
    private static class UserApiClientWithMockableHttpClient extends UserApiClient {
        private HttpClient httpClient;

        public UserApiClientWithMockableHttpClient(String baseUrl) {
            super(baseUrl);
        }

        @Override
        protected HttpClient getHttpClient() {
            return httpClient;
        }

        public void setHttpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
        }
    }
}