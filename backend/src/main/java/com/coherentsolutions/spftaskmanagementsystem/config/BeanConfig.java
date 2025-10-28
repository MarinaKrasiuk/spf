package com.coherentsolutions.spftaskmanagementsystem.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class BeanConfig {
    @PostConstruct
    public void init() {
       log.info("BeanConfig initialized");
    }

    @PreDestroy
    public void cleanup() {
        log.info("BeanConfig destroyed");
    }
}
