package com.coherentsolutions.spftaskmanagementsystem.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class AppLifecycleLogger {
    /**
     * Configuration class for application lifecycle logging and future bean definitions.
     */
    private final Environment environment;

    @PostConstruct
    public void init() {
       log.info("Application started. Active profiles: [{}] ", environment.getActiveProfiles());
    }

    @PreDestroy
    public void cleanup() {
        log.info("BeanConfig destroyed");
    }
}
