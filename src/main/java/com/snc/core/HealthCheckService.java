package com.snc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class HealthCheckService extends BaseService {
    public Map<String, String> checkSystemHealth() {
        Map<String, String> healthStatus = new HashMap<>();
        healthStatus.put("status", "OK");
        healthStatus.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        logInfo("Health check performed: " + healthStatus);
        return healthStatus;
    }
}
