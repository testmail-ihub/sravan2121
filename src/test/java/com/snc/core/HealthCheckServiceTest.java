package com.snc.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class HealthCheckServiceTest {
    @Test
    public void testCheckSystemHealth() {
        HealthCheckService service = new HealthCheckService();
        Map<String, String> healthStatus = service.checkSystemHealth();
        assertNotNull(healthStatus);
        assertEquals("OK", healthStatus.get("status"));
        assertNotNull(healthStatus.get("timestamp"));
        // Optionally, print the health status for debugging
        System.out.println("Health Status: " + healthStatus);
    }
}
