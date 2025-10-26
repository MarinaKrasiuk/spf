package com.coherentsolutions.spftaskmanagementsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for logging audit messages in the Task Management System.
 */
@Service
@Slf4j
public class AuditService {
    /**
     * Logs an audit message.
     * @param message the message to log
     */
    public void log(String message) {
        log.info("AUDIT: [{}] " , message);
    }
}
