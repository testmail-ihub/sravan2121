import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class UserApiClient {

    private final HttpClient httpClient;
    private final String baseUrl;

    public UserApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public CompletableFuture<String> getUser(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + "/users/" + id))
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        return sendRequestWithRetry(request);
    }

    public CompletableFuture<String> createUser(String jsonBody) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .uri(URI.create(baseUrl + "/users"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        return sendRequestWithRetry(request);
    }

    private CompletableFuture<String> sendRequestWithRetry(HttpRequest request) {
        return sendRequest(request, 0);
    }

    private CompletableFuture<String> sendRequest(HttpRequest request, int retryCount) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("Request URL: " + request.uri());
                    request.headers().map().forEach((key, value) -> System.out.println("Request Header: " + key + ": " + value));
                    System.out.println("Response Status Code: " + response.statusCode());
                    response.headers().map().forEach((key, value) -> System.out.println("Response Header: " + key + ": " + value));

                    if (response.statusCode() >= 400) {
                        System.err.println("Error response: " + response.body());
                        if (response.statusCode() >= 500 && retryCount < 3) {
                            System.out.println("Retrying request... (Attempt " + (retryCount + 1) + ")");
                            try {
                                TimeUnit.SECONDS.sleep(1); // Simple backoff
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            return sendRequest(request, retryCount + 1).join();
                        }
                        throw new RuntimeException("API Error: " + response.statusCode() + " - " + response.body());
                    }
                    return response.body();
                })
                .exceptionally(e -> {
                    System.err.println("Request failed: " + e.getMessage());
                    if (e.getCause() instanceof java.net.http.HttpTimeoutException && retryCount < 3) {
                        System.out.println("Retrying request due to timeout... (Attempt " + (retryCount + 1) + ")");
                        try {
                            TimeUnit.SECONDS.sleep(1); // Simple backoff
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                        return sendRequest(request, retryCount + 1).join();
                    }
                    throw new RuntimeException("Request failed: " + e.getMessage(), e);
                });
    }

    public static void main(String[] args) throws Exception {
        UserApiClient client = new UserApiClient("https://api.example.com");

        // Example GET request
        client.getUser(101).thenAccept(System.out::println).join();

        // Example POST request
        String newUserJson = "{\"id\": 101, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"role\": \"admin\"}";
        client.createUser(newUserJson).thenAccept(System.out::println).join();
    }
}