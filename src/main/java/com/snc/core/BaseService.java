package com.snc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Basic utility method example
    public String getServiceStatus() {
        return "Service is running.";
    }
}