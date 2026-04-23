package com.smartload;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartUpLogger {

    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        String port = System.getProperty("server.port", "8080");

        System.out.println("\n=======================================");
        System.out.println("🚀 SmartLoad Optimizer Started!");
        System.out.println("👉 Health Check: http://localhost:" + port + "/actuator/health");
        System.out.println("👉 Optimize API: http://localhost:" + port + "/api/v1/load-optimizer/optimize");
        System.out.println("=======================================\n");
    }
}
