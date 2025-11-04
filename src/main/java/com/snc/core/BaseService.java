package com.snc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * BaseService - Foundation class for all services in the SNC core package.
 * Provides common logging, validation, and utility functionality.
 * 
 * This class establishes enterprise-standard patterns for:
 * - Centralized logging with SLF4J
 * - Common validation methods
 * - Thread-safe operations
 * - Standardized error handling
 */
public abstract class BaseService {
    
    protected final Logger logger;
    private final ConcurrentMap<String, Object> serviceCache;
    private final DateTimeFormatter timestampFormatter;
    
    /**
     * Constructor initializes logger and common utilities
     */
    protected BaseService() {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.serviceCache = new ConcurrentHashMap<>();
        this.timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        logger.info("Initializing service: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Validates that a string parameter is not null or empty
     * @param value the string to validate
     * @param parameterName the name of the parameter for error messages
     * @throws IllegalArgumentException if validation fails
     */
    protected void validateNotEmpty(String value, String parameterName) {
        if (value == null || value.trim().isEmpty()) {
            String errorMsg = String.format("Parameter '%s' cannot be null or empty", parameterName);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }
    
    /**
     * Validates that an object parameter is not null
     * @param value the object to validate
     * @param parameterName the name of the parameter for error messages
     * @throws IllegalArgumentException if validation fails
     */
    protected void validateNotNull(Object value, String parameterName) {
        if (value == null) {
            String errorMsg = String.format("Parameter '%s' cannot be null", parameterName);
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
    }
    
    /**
     * Gets the current timestamp as a formatted string
     * @return formatted timestamp string
     */
    protected String getCurrentTimestamp() {
        return LocalDateTime.now().format(timestampFormatter);
    }
    
    /**
     * Safely retrieves a cached value
     * @param key the cache key
     * @return the cached value or null if not found
     */
    protected Object getCachedValue(String key) {
        validateNotEmpty(key, "cacheKey");
        return serviceCache.get(key);
    }
    
    /**
     * Safely stores a value in cache
     * @param key the cache key
     * @param value the value to cache
     */
    protected void setCachedValue(String key, Object value) {
        validateNotEmpty(key, "cacheKey");
        validateNotNull(value, "cacheValue");
        serviceCache.put(key, value);
        logger.debug("Cached value for key: {}", key);
    }
    
    /**
     * Handles exceptions with consistent logging and re-throwing
     * @param operation the operation that failed
     * @param exception the exception that occurred
     * @throws RuntimeException wrapping the original exception
     */
    protected void handleException(String operation, Exception exception) {
        String errorMsg = String.format("Operation '%s' failed: %s", operation, exception.getMessage());
        logger.error(errorMsg, exception);
        throw new RuntimeException(errorMsg, exception);
    }
    
    /**
     * Logs method entry with parameters
     * @param methodName the name of the method
     * @param parameters the method parameters
     */
    protected void logMethodEntry(String methodName, Object... parameters) {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering method: {} with parameters: {}", methodName, 
                        parameters != null ? java.util.Arrays.toString(parameters) : "none");
        }
    }
    
    /**
     * Logs method exit with result
     * @param methodName the name of the method
     * @param result the method result
     */
    protected void logMethodExit(String methodName, Object result) {
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting method: {} with result: {}", methodName, result);
        }
    }
    
    /**
     * Gets the service name for identification purposes
     * @return the simple class name of the service
     */
    public String getServiceName() {
        return this.getClass().getSimpleName();
    }
    
    /**
     * Clears the service cache
     */
    protected void clearCache() {
        serviceCache.clear();
        logger.info("Service cache cleared for: {}", getServiceName());
    }
}