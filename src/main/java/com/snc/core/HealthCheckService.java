package com.snc.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * HealthCheckService provides a simple health check endpoint.
 * It extends BaseService to reuse the logger and utility methods.
 */
public class HealthCheckService extends BaseService {

    /**
     * Returns a mock health status as a JSON string.
     * @return JSON representation of health status.
     */
    public String checkSystemHealth() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode health = mapper.createObjectNode();
            health.put("status", "UP");
            health.put("timestamp", System.currentTimeMillis());
            health.put("service", "HealthCheckService");
            return mapper.writeValueAsString(health);
        } catch (Exception e) {
            logger.error("Failed to generate health check JSON", e);
            return "{\"status\":\"ERROR\"}";
        }
    }
}
