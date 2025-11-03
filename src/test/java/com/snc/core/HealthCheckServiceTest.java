package com.snc.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link HealthCheckService}.
 */
public class HealthCheckServiceTest {

    @Test
    public void testCheckSystemHealthReturnsValidJson() {
        HealthCheckService service = new HealthCheckService();
        String json = service.checkSystemHealth();
        assertNotNull(json, "The health check JSON should not be null");
        assertTrue(json.contains("\"status\":\"UP\""), "JSON should contain status UP");
        assertTrue(json.contains("\"service\":\"HealthCheckService\""), "JSON should contain service name");
        // Basic validation that timestamp is present and is a number
        assertTrue(json.matches(".*\\\"timestamp\\\":\\d+.*"), "JSON should contain a numeric timestamp");
    }
}
