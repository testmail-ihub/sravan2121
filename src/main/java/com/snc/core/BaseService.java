package com.snc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseService provides common utilities and a logger for all service classes.
 */
public abstract class BaseService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Utility method to safely convert an object to a string.
     */
    protected String safeToString(Object obj) {
        return obj == null ? "null" : obj.toString();
    }

    // Additional shared utilities can be added here.
}
