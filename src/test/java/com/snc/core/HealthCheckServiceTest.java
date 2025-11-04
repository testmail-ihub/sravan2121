package com.snc.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

/**
 * HealthCheckServiceTest - Comprehensive JUnit 5 test class for HealthCheckService.
 * Tests the health monitoring capabilities, JSON response structure, and error handling.
 * 
 * This test class validates:
 * - checkSystemHealth() method functionality
 * - JSON response structure and content
 * - System components status validation
 * - Caching behavior and cache management
 * - Error handling scenarios
 */
class HealthCheckServiceTest {
    
    private HealthCheckService healthCheckService;
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        healthCheckService = new HealthCheckService();
        objectMapper = new ObjectMapper();
    }
    
    @Nested
    @DisplayName("Health Check Method Tests")
    class HealthCheckMethodTests {
        
        @Test
        @DisplayName("Should return valid JSON response when checkSystemHealth is called")
        void testCheckSystemHealthReturnsValidJson() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            
            // Assert
            assertNotNull(result, "Health check result should not be null");
            assertFalse(result.isEmpty(), "Health check result should not be empty");
            
            // Validate JSON structure
            JsonNode jsonNode = objectMapper.readTree(result);
            assertNotNull(jsonNode, "Result should be valid JSON");
        }
        
        @Test
        @DisplayName("Should return UP status in health check response")
        void testCheckSystemHealthReturnsUpStatus() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            
            // Assert
            assertEquals("UP", jsonNode.get("status").asText(), "Status should be UP");
        }
        
        @Test
        @DisplayName("Should include timestamp in health check response")
        void testCheckSystemHealthIncludesTimestamp() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            
            // Assert
            assertTrue(jsonNode.has("timestamp"), "Response should include timestamp");
            assertNotNull(jsonNode.get("timestamp").asText(), "Timestamp should not be null");
            assertFalse(jsonNode.get("timestamp").asText().isEmpty(), "Timestamp should not be empty");
        }
        
        @Test
        @DisplayName("Should include service name in health check response")
        void testCheckSystemHealthIncludesServiceName() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            
            // Assert
            assertTrue(jsonNode.has("service"), "Response should include service name");
            assertEquals("HealthCheckService", jsonNode.get("service").asText(), "Service name should be HealthCheckService");
        }
    }
    
    @Nested
    @DisplayName("System Components Tests")
    class SystemComponentsTests {
        
        @Test
        @DisplayName("Should include all required system components")
        void testCheckSystemHealthIncludesAllComponents() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            
            // Assert
            assertTrue(jsonNode.has("components"), "Response should include components");
            JsonNode components = jsonNode.get("components");
            
            // Verify all expected components are present
            assertTrue(components.has("database"), "Should include database component");
            assertTrue(components.has("cache"), "Should include cache component");
            assertTrue(components.has("external_api"), "Should include external_api component");
            assertTrue(components.has("disk_space"), "Should include disk_space component");
            assertTrue(components.has("memory"), "Should include memory component");
        }
        
        @Test
        @DisplayName("Should have UP status for all system components")
        void testAllComponentsHaveUpStatus() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            JsonNode components = jsonNode.get("components");
            
            // Assert each component has UP status
            assertEquals("UP", components.get("database").get("status").asText(), "Database should be UP");
            assertEquals("UP", components.get("cache").get("status").asText(), "Cache should be UP");
            assertEquals("UP", components.get("external_api").get("status").asText(), "External API should be UP");
            assertEquals("UP", components.get("disk_space").get("status").asText(), "Disk space should be UP");
            assertEquals("UP", components.get("memory").get("status").asText(), "Memory should be UP");
        }
        
        @Test
        @DisplayName("Should include details and last_checked for each component")
        void testComponentsIncludeDetailsAndTimestamp() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            JsonNode components = jsonNode.get("components");
            
            // Assert each component has required fields
            JsonNode database = components.get("database");
            assertTrue(database.has("details"), "Database component should have details");
            assertTrue(database.has("last_checked"), "Database component should have last_checked");
            assertEquals("Connection pool healthy", database.get("details").asText(), "Database details should match expected value");
        }
    }
    
    @Nested
    @DisplayName("System Metrics Tests")
    class SystemMetricsTests {
        
        @Test
        @DisplayName("Should include system metrics in health check response")
        void testCheckSystemHealthIncludesMetrics() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            
            // Assert
            assertTrue(jsonNode.has("metrics"), "Response should include metrics");
            JsonNode metrics = jsonNode.get("metrics");
            
            assertTrue(metrics.has("uptime_seconds"), "Should include uptime_seconds");
            assertTrue(metrics.has("active_connections"), "Should include active_connections");
            assertTrue(metrics.has("requests_per_minute"), "Should include requests_per_minute");
            assertTrue(metrics.has("error_rate_percent"), "Should include error_rate_percent");
        }
        
        @Test
        @DisplayName("Should have correct metric values")
        void testMetricsHaveCorrectValues() throws Exception {
            // Act
            String result = healthCheckService.checkSystemHealth();
            JsonNode jsonNode = objectMapper.readTree(result);
            JsonNode metrics = jsonNode.get("metrics");
            
            // Assert metric values
            assertEquals(86400, metrics.get("uptime_seconds").asInt(), "Uptime should be 86400 seconds");
            assertEquals(42, metrics.get("active_connections").asInt(), "Active connections should be 42");
            assertEquals(150, metrics.get("requests_per_minute").asInt(), "Requests per minute should be 150");
            assertEquals(0.1, metrics.get("error_rate_percent").asDouble(), 0.001, "Error rate should be 0.1%");
        }
    }
    
    @Nested
    @DisplayName("Caching Functionality Tests")
    class CachingTests {
        
        @Test
        @DisplayName("Should cache health check result after execution")
        void testHealthCheckResultIsCached() {
            // Act
            String result1 = healthCheckService.checkSystemHealth();
            String cachedResult = healthCheckService.getLastHealthCheck();
            
            // Assert
            assertNotNull(cachedResult, "Cached result should not be null");
            assertEquals(result1, cachedResult, "Cached result should match the original result");
        }
        
        @Test
        @DisplayName("Should return null when no cached result exists initially")
        void testGetLastHealthCheckReturnsNullInitially() {
            // Act
            String cachedResult = healthCheckService.getLastHealthCheck();
            
            // Assert
            assertNull(cachedResult, "Initial cached result should be null");
        }
        
        @Test
        @DisplayName("Should clear cache when clearHealthCheckCache is called")
        void testClearHealthCheckCache() {
            // Arrange
            healthCheckService.checkSystemHealth(); // Generate and cache result
            assertNotNull(healthCheckService.getLastHealthCheck(), "Should have cached result");
            
            // Act
            healthCheckService.clearHealthCheckCache();
            
            // Assert
            assertNull(healthCheckService.getLastHealthCheck(), "Cached result should be null after clearing");
        }
    }
    
    @Nested
    @DisplayName("Service Integration Tests")
    class ServiceIntegrationTests {
        
        @Test
        @DisplayName("Should inherit service name from BaseService")
        void testServiceNameInheritance() {
            // Act
            String serviceName = healthCheckService.getServiceName();
            
            // Assert
            assertEquals("HealthCheckService", serviceName, "Service name should be HealthCheckService");
        }
        
        @Test
        @DisplayName("Should execute multiple health checks consistently")
        void testMultipleHealthChecksConsistency() throws Exception {
            // Act
            String result1 = healthCheckService.checkSystemHealth();
            String result2 = healthCheckService.checkSystemHealth();
            
            // Assert
            assertNotNull(result1, "First result should not be null");
            assertNotNull(result2, "Second result should not be null");
            
            // Parse and verify both results have same structure
            JsonNode json1 = objectMapper.readTree(result1);
            JsonNode json2 = objectMapper.readTree(result2);
            
            assertEquals(json1.get("status").asText(), json2.get("status").asText(), "Status should be consistent");
            assertEquals(json1.get("service").asText(), json2.get("service").asText(), "Service name should be consistent");
            assertTrue(json1.has("components") && json2.has("components"), "Both should have components");
            assertTrue(json1.has("metrics") && json2.has("metrics"), "Both should have metrics");
        }
    }
}