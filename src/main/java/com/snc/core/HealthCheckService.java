package com.snc.core;

import org.json.JSONObject;

public class HealthCheckService extends BaseService {

    public JSONObject checkSystemHealth() {
        logger.info("Performing system health check...");
        JSONObject healthData = new JSONObject();
        healthData.put("status", "UP");
        healthData.put("timestamp", System.currentTimeMillis());
        healthData.put("serviceName", "HealthCheckService");
        logger.info("System health check completed. Status: {}", healthData.getString("status"));
        return healthData;
    }
}