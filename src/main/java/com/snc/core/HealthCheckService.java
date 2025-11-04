package com.snc.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;

/**
 * HealthCheckService - Concrete service implementation that extends BaseService.
 * Provides system health monitoring capabilities with JSON response format.
 * 
 * This service leverages BaseService foundation for:
 * - Centralized logging via inherited logger
 * - Parameter validation methods
 * - Exception handling patterns
 * - Method tracing capabilities
 */
public class HealthCheckService extends BaseService {
    
    private final ObjectMapper objectMapper;
    private static final String HEALTH_CHECK_CACHE_KEY = "last_health_check";
    
    /**
     * Constructor initializes the service and JSON mapper
     */
    public HealthCheckService() {
        super(); // Initialize BaseService (logger, cache, utilities)
        this.objectMapper = new ObjectMapper();
        logger.info("HealthCheckService initialized successfully");
    }
    
    /**
     * Performs system health check and returns mock JSON response
     * @return JSON string containing system health status and components
     */
    public String checkSystemHealth() {
        logMethodEntry("checkSystemHealth");
        
        try {
            // Create health check response structure
            Map<String, Object> healthResponse = new HashMap<>();
            
            // Basic system information
            healthResponse.put("status", "UP");
            healthResponse.put("timestamp", getCurrentTimestamp());
            healthResponse.put("service", getServiceName());
            
            // Mock system components status
            Map<String, Object> components = new HashMap<>();
            components.put("database", createComponentStatus("UP", "Connection pool healthy"));
            components.put("cache", createComponentStatus("UP", "Redis connection active"));
            components.put("external_api", createComponentStatus("UP", "All endpoints responding"));
            components.put("disk_space", createComponentStatus("UP", "85% available"));
            components.put("memory", createComponentStatus("UP", "Memory usage normal"));
            
            healthResponse.put("components", components);
            
            // System metrics (mock data)
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("uptime_seconds", 86400); // 24 hours
            metrics.put("active_connections", 42);
            metrics.put("requests_per_minute", 150);
            metrics.put("error_rate_percent", 0.1);
            
            healthResponse.put("metrics", metrics);
            
            // Convert to JSON string
            String jsonResponse = objectMapper.writeValueAsString(healthResponse);
            
            // Cache the result
            setCachedValue(HEALTH_CHECK_CACHE_KEY, jsonResponse);
            
            logger.info("Health check completed successfully - Status: UP");
            logMethodExit("checkSystemHealth", "JSON response generated");
            
            return jsonResponse;
            
        } catch (Exception e) {
            logger.error("Health check failed: {}", e.getMessage(), e);
            return createErrorResponse(e);
        }
    }
    
    /**
     * Creates a component status object
     * @param status the component status (UP/DOWN/DEGRADED)
     * @param details additional status details
     * @return component status map
     */
    private Map<String, Object> createComponentStatus(String status, String details) {
        validateNotEmpty(status, "status");
        validateNotEmpty(details, "details");
        
        Map<String, Object> componentStatus = new HashMap<>();
        componentStatus.put("status", status);
        componentStatus.put("details", details);
        componentStatus.put("last_checked", getCurrentTimestamp());
        
        return componentStatus;
    }
    
    /**
     * Creates an error response when health check fails
     * @param exception the exception that occurred
     * @return JSON error response string
     */
    private String createErrorResponse(Exception exception) {
        try {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "DOWN");
            errorResponse.put("timestamp", getCurrentTimestamp());
            errorResponse.put("service", getServiceName());
            errorResponse.put("error", exception.getMessage());
            errorResponse.put("error_type", exception.getClass().getSimpleName());
            
            return objectMapper.writeValueAsString(errorResponse);
            
        } catch (Exception jsonException) {
            logger.error("Failed to create error response JSON: {}", jsonException.getMessage());
            return "{\"status\":\"DOWN\",\"error\":\"Health check failed and error response generation failed\",\"timestamp\":\"" + getCurrentTimestamp() + "\"}";
        }
    }
    
    /**
     * Gets the last cached health check result
     * @return cached health check JSON or null if not available
     */
    public String getLastHealthCheck() {
        logMethodEntry("getLastHealthCheck");
        
        Object cachedResult = getCachedValue(HEALTH_CHECK_CACHE_KEY);
        String result = cachedResult != null ? cachedResult.toString() : null;
        
        logMethodExit("getLastHealthCheck", result != null ? "Cached result found" : "No cached result");
        return result;
    }
    
    /**
     * Clears the health check cache
     */
    public void clearHealthCheckCache() {
        logMethodEntry("clearHealthCheckCache");
        clearCache();
        logger.info("Health check cache cleared");
        logMethodExit("clearHealthCheckCache", "Cache cleared");
    }
}